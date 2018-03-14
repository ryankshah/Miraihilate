from netaddr import IPNetwork
from paramiko.ssh_exception import AuthenticationException, SSHException, BadHostKeyException
from telnetlib import Telnet
from datetime import datetime
from mysql.connector import Error
import mysql.connector
import paramiko
import json
import time
import sys
import random
import string

BRUTEFORCE_TIMEOUT = 3
# A list of root account username/password combinations for IoT devices
IOT_ROOT_USER_COMBINATIONS = ('admin1', 'password'), ('root', 'xc3511'), ('root', 'vizxv'), ('root', 'admin'), ('admin', 'admin'), ('root', '888888'), ('root', 'xmhdipc'), ('root', 'default'), ('root', 'juantech'), ('root', '123456'), ('root', '54321'), ('support', 'support'), ('root', ''), ('admin', 'password'), ('root', 'root'), ('root', '12345'), ('user', 'user'), ('admin', '(none)'), ('root', 'pass'), ('admin', 'admin1234'), ('root', '1111'), ('admin', 'smcadmin'), ('admin', '1111'), ('root', '666666'), ('root', 'password'), ('root', '1234'), ('root', 'klv123'), ('Administrator', 'admin'), ('service', 'service'), ('supervisor', 'supervisor'), ('guest', 'guest'), ('guest', '12345'), ('guest', '12345'), ('admin1', 'password'), ('administrator', '1234'), ('666666', '666666'), ('888888', '888888'), ('ubnt', 'ubnt'), ('root', 'klv1234'), ('root', 'Zte521'), ('root', 'hi3518'), ('root', 'jvbzd'), ('root', 'anko'), ('root', 'zlxx.'), ('root', '7ujMko0vizxv'), ('root', '7ujMko0admin'), ('root', 'system'), ('root', 'ikwb'), ('root', 'dreambox'), ('root', 'user'), ('root', 'realtek'), ('root', '00000000'), ('admin', '1111111'), ('admin', '1234'), ('admin', '12345'), ('admin', '54321'), ('admin', '123456'), ('admin', '7ujMko0admin'), ('admin', '1234'), ('admin', 'pass'), ('admin', 'meinsm'), ('tech', 'tech'), ('mother', 'fucker')

def log_to_db(uuid, data, start_timestamp, end_timestamp):
    try:
        config = {
          'user': 'root',
          'password': 'root',
          'host': 'localhost',
          'database': 'miraihilate',
          'port': 8889 # MAMP MySQL port
        }

        conn = mysql.connector.connect(**config)

        cursor = conn.cursor(prepared=True)

        sql = 'INSERT INTO scan_logs (user_uuid, data, start_timestamp, end_timestamp) VALUES (%s, %s, %s, %s)'

        cursor.execute(sql, (uuid, data, start_timestamp, end_timestamp))
        conn.commit()

        cursor.close()
        conn.close()
    except Error as e:
        print(e)

# SSH Scanning Function
# ======================
# Arguments
#   - address: IP address
#   - cidr: CIDR prefix to specify address range
#   - cp: Change password on device and notify of change?
#   - nd: Notify device of vulnerability?
#   - to: SSH timeout
#   - cmds: Extra commands to execute
#
# Description
#   - Scans a specified network range and attempts to bruteforce into
#     the root accounts found on the IP addresses found using the default
#     list of username/password combinations defined as `passwords`, using
#     an SSH connection
#
# TODO: Fix request timeout errors
def scan_ssh(address, cidr, cp, nd, to, cmds):
    start_timestamp = datetime.now()
    log = []
    iprange = IPNetwork(address + '/' + cidr)
    vulnerable_count = 0

    log.append('Advanced scan on network: ' + address + '/' + cidr + ' at ' + start_timestamp)
    log.append('')

    log.append('Advanced Scan Specification')
    log.append('')
    log.append('Scanning Mode: SSH')
    log.append('IP address range: ' + str(iprange[0]) + ' - ' + str(iprange[-1]))

    if cp == '0':
        log.append('Changing device passwords? - No')
    else:
        log.append('Changing device passwords? - Yes')

    if nd == '0':
        log.append('Notify device about vulnerability? - No')
    else:
        log.append('Notify device about vulnerability? - Yes')

	# TODO: Check if timeout or extra parameters are set

    log.append('')
    log.append('Scan Results')
    log.append('')

    # Loop through ip addresses in the network range specified
    for ip in iprange:
        log.append('Trying SSH for IP ' + str(ip) + ' ...')

        for acc in IOT_ROOT_USER_COMBINATIONS:
            try:
                # Create a new SSHClient
                device = paramiko.SSHClient()

                # Load host keys from a read-only system file
                device.load_system_host_keys()

                # When SSH server's hostname is not in system host keys or application
                # host keys, automatically add the hostname and new key to the local
                # HostKeys object and save it
                device.set_missing_host_key_policy(paramiko.AutoAddPolicy())

                # Connect to an SSH server and authenticate to it
                # Port 2281 is used for VM testing (actual SSH port is 22)
                device.connect(str(ip), username=acc[0], password=acc[1], port=2281, timeout=BRUTEFORCE_TIMEOUT)

                log.append('  - This device is vulnerable!')
                vulnerable_count += 1

                # Check if notify device option was checked
                # TODO: Fix notifying device
                if nd == '1':
                    device.exec_command("notify-send 'Miraihilate Alert!' 'A vulnerability has been detected in your device, which has been linked with Mirai malware.'")
                    log.append('  - The device was alerted about the vulnerability.')
                else:
                    log.append('  - This device was not alerted about the vulnerability.')

                # TODO: Check if user want's to change device password `cp`

                # Close the SSHClient and its underlying Transport
                device.close()

                log.append('  - Closing device...')

                break
            except AuthenticationException:
                continue
            except (SSHException, BadHostKeyException, Exception):
                log.append('  - Could not connect to device via SSH!')
                break

    return []
