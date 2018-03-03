from netaddr import IPNetwork
from paramiko.ssh_exception import AuthenticationException, SSHException, BadHostKeyException
from telnetlib import Telnet
import paramiko
import socket
import json
import time

BRUTEFORCE_TIMEOUT = 3
# A list of root account username/password combinations for IoT devices
IOT_ROOT_USER_COMBINATIONS = ('admin1', 'password'), ('root', 'xc3511'), ('root', 'vizxv'), ('root', 'admin'), ('admin', 'admin'), ('root', '888888'), ('root', 'xmhdipc'), ('root', 'default'), ('root', 'juantech'), ('root', '123456'), ('root', '54321'), ('support', 'support'), ('root', ''), ('admin', 'password'), ('root', 'root'), ('root', '12345'), ('user', 'user'), ('admin', '(none)'), ('root', 'pass'), ('admin', 'admin1234'), ('root', '1111'), ('admin', 'smcadmin'), ('admin', '1111'), ('root', '666666'), ('root', 'password'), ('root', '1234'), ('root', 'klv123'), ('Administrator', 'admin'), ('service', 'service'), ('supervisor', 'supervisor'), ('guest', 'guest'), ('guest', '12345'), ('guest', '12345'), ('admin1', 'password'), ('administrator', '1234'), ('666666', '666666'), ('888888', '888888'), ('ubnt', 'ubnt'), ('root', 'klv1234'), ('root', 'Zte521'), ('root', 'hi3518'), ('root', 'jvbzd'), ('root', 'anko'), ('root', 'zlxx.'), ('root', '7ujMko0vizxv'), ('root', '7ujMko0admin'), ('root', 'system'), ('root', 'ikwb'), ('root', 'dreambox'), ('root', 'user'), ('root', 'realtek'), ('root', '00000000'), ('admin', '1111111'), ('admin', '1234'), ('admin', '12345'), ('admin', '54321'), ('admin', '123456'), ('admin', '7ujMko0admin'), ('admin', '1234'), ('admin', 'pass'), ('admin', 'meinsm'), ('tech', 'tech'), ('mother', 'fucker')

# SSH Scanning Function
# ======================
# Arguments
#   - address: IP address
#   - cidr: CIDR prefix to specify address range
#   - cp: Change password on device and notify of change?
#   - nd: Notify device of vulnerability?
#
# Description
#   - Scans a specified network range and attempts to bruteforce into
#     the root accounts found on the IP addresses found using the default
#     list of username/password combinations defined as `passwords`, using
#     an SSH connection
#
# TODO: Fix request timeout errors
def scan_ssh(address, cidr, cp, nd):
    # A list of triples to identify a vulnerable device was found
    # in the form of (hostname, ip, mode)
    vulnerables = []

    # Loop through ip addresses in the network range specified
    for ip in IPNetwork(address + '/' + cidr):
        print('Trying SSH for IP "' + str(ip) + '" ...')

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
                device.connect(str(ip), username=acc[0], password=acc[1], port=22, timeout=BRUTEFORCE_TIMEOUT)

                # Check if notify device option was checked
                # TODO: Fix notifying device
                if nd == 1:
                    # device.exec_command('')
                    device.exec_command("notify-send 'Miraihilate Alert!' 'A vulnerability has been detected in your device, linked with Mirai malware.'")

                # Returns a triple (hostname, aliaslist, ipaddrlist)
                #   - See python documentation for socket
                host_info = socket.gethostbyaddr(str(ip))

                # Append a triple ('hostname', 'ip_address', 'SSH/Telnet')
                vulnerables.append((host_info[0], str(ip), 'SSH'))

                # Close the SSHClient and its underlying Transport
                device.close()

                print('Vulnerable found on IP ' + str(ip) + '!')

                break
            except AuthenticationException:
                continue
            except (SSHException, BadHostKeyException, Exception):
                break

    # Return the vulnerable list and JSON output
    # or if nothing was found then return empty JSON
    return vulnerables

# TODO: Get command line arguments
# TODO: Pass to scan function to run the appropriate scan

# print('Vulnerables: ' + str(scan_ssh('127.0.0.1', '24', 0, 1)))
