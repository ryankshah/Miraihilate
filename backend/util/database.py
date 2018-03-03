from mysql.connector import Error
import mysql.connector

def connect():
    try:
        import mysql.connector

        config = {
          'user': 'root',
          'password': 'root',
          'host': 'localhost',
          'database': 'mysql',
          'raise_on_warnings': True,
          'port': 8889
        }

        conn = mysql.connector.connect(**config)

        if conn.is_connected():
            print('Connected!')
        
        conn.close()
    except Error as e:
        print(e)

connect()
