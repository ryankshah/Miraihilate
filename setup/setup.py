from mysql.connector import Error
import mysql.connector
import sys
import uuid
import secrets
import string
import bcrypt

from colorama import init
init(strip=not sys.stdout.isatty()) # strip colors if stdout is redirected
from termcolor import cprint
from pyfiglet import figlet_format

def gen_sec_pwd():
    data = string.ascii_letters + string.punctuation + string.digits
    while True:
        # Generate 16 character long password from the alphabet defined by `data`
        pwd = ''.join(secrets.choice(data) for i in range(16))
        if any(c.isupper() for c in pwd):
            return pwd

def create_user(uuid, first, last, email):
    try:
        config = {
          'user': 'root',
          'password': 'root',
          'host': 'localhost',
          'database': 'miraihilate',
          'port': 8889 # MAMP MySQL port
        }

        conn = mysql.connector.connect(**config)

        # if conn.is_connected():
            # print('Connected!')

        cursor = conn.cursor(prepared=True)

        sql = 'INSERT INTO users (uuid, firstName, lastName, email, password, uac_rank) VALUES (%s, %s, %s, %s, %s, %s)'

        # Generate raw, cryptographically secure password
        raw_pwd = gen_sec_pwd()
        # Hash a password for the first time, with a randomly-generated salt
        pwd_hash = bcrypt.hashpw(raw_pwd.encode('ascii'), bcrypt.gensalt(prefix=b"2a"))

        cursor.execute(sql, (str(uuid), first, last, email, pwd_hash, 1,))
        conn.commit()

        print('\nPassword generated for first login: ' + raw_pwd + '\n')

        print('\nMiraihilate has been successfully downloaded to: ...')

        cursor.close()
        conn.close()
    except Error as e:
        print(e)

# ====================== #
#  Start setup sequence  #
# ====================== #

cprint(figlet_format('Miraihilate', font='big'), attrs=['bold'])

print('Welcome to the Miraihilate setup utility! Follow the instructions to setup the ' +
          'first admin account on the system, as well as download the program on your host machine...\n\n')

first_name = input('Please enter your first name: ')
last_name = input('Please enter your last name: ')
email = input('Please enter your email address: ')

proceed = input('\nAre you sure you wish to proceed with the setup? (Y/N) ')

if proceed == 'N' or proceed == 'n':
    print('Setup aborted... Terminating.\n')
    sys.exit()
elif proceed == 'Y' or proceed == 'y':
    create_user(uuid.uuid4(), first_name, last_name, email)
else:
    print('Invalid option! Terminating.\n')
    sys.exit()
