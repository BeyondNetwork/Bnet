from flask import Flask, jsonify, request
import mysql.connector
from send_mail import send_verification_mail
from random import SystemRandom
import file
import hasher

app = Flask(__name__)

user_table = "userData"
col_names = ['_id', 'user_name', 'email_id', 'password_hash', 'verified', 'random_number']


@app.route('/')
def home():
    return 'Home'


@app.route('/register', methods=['POST'])
def register():
    response = {"success": 0, "message": "An error has occurred. Please try again.."}
    try:
        if request.method == 'POST':
            user_name = request.form['user_name']
            email_id = request.form['email_id']
            password_hash = request.form['password_hash']

            db = mysql.connector.connect(
                host="merchantaliabbas.mysql.pythonanywhere-services.com",
                user="merchantaliabbas",
                passwd="rootroot",
                database="merchantaliabbas$betaNet"
            )
            db_cursor = db.cursor()
            db_cursor.execute("INSERT INTO {} ({}) SELECT * FROM (SELECT '{}') AS tmp WHERE NOT EXISTS (SELECT {} "
                              "FROM {} WHERE {} = '{}') LIMIT 1;".format(user_table, 'email_id', email_id,
                                                                         'email_id', user_table, 'email_id',
                                                                         email_id))
            db_cursor.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}';"
                              .format(user_table, 'user_name', user_name, 'email_id', email_id))
            db_cursor.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}';"
                              .format(user_table, 'password_hash', password_hash, 'email_id', email_id))
            db.commit()
            db_cursor = db.cursor()
            db_cursor.execute("SELECT * FROM {} WHERE {} = '{}';".
                              format(user_table, 'email_id', email_id))
            data = db_cursor.fetchone()

            _id = data[0]
            if str(data[5]) == '0' or str(data[5]) == 0:
                
                random_number = SystemRandom().randrange(100 ** file.power)
                db_cursor.execute("UPDATE {} SET {} = '{}' WHERE {} = '{}';"
                                  .format(user_table, 'random_number', random_number, 'email_id', email_id))
            else:
                random_number = data[5]

            db.commit()
            code = hasher.hash_for_verification(_id, email_id, random_number)
            send_verification_mail(email_id, code, send=True)
            response["message"] = "User created successfully!\nAn email verification link has been sent to you."
            response["success"] = 1
        else:
            response["message"] = "Error accessing data"  # should not ideally happen
    except Exception as e:
        pass
    finally:
        try:
            db.close()
        except Exception as e:
            pass
    return jsonify(response)


@app.route('/login', methods=['POST'])
def login():
    response = {"success": 0, "message": "An error has occurred. Please try again.."}
    try:
        if request.method == 'POST':
            email_id = request.form['email_id']
            password_hash = request.form['password_hash']

            db = mysql.connector.connect(
                host="merchantaliabbas.mysql.pythonanywhere-services.com",
                user="merchantaliabbas",
                passwd="rootroot",
                database="merchantaliabbas$betaNet"
            )
            db_cursor = db.cursor()
            db_cursor.execute("SELECT {} FROM {} WHERE {} = '{}';".
                              format('password_hash', user_table, 'email_id', email_id))
            data = db_cursor.fetchone()
            db_cursor.execute("SELECT {} FROM {} WHERE {} = '{}';".
                              format('verified', user_table, 'email_id', email_id))
            data2 = db_cursor.fetchone()

            if data is None or data2 is None:
                response["message"] = "No such email_id has been registered"
            else:
                if data[0] == password_hash and data2[0] == 1:
                    response["message"] = "Valid"
                    response["success"] = 1
                elif data2[0] != 1 and data[0] == password_hash:
                    response["message"] = "Your email id has not been verified as yet.\n" \
                                          "Please go to the verification link sent to you via email."
                else:
                    response["message"] = "Wrong Password!"
        else:
            response["message"] = "Error accessing data"  # should not ideally happen
    except Exception as e:
        pass
    finally:
        try:
            db.close()
        except Exception as e:
            pass
    return jsonify(response)


@app.route('/verify', methods=['GET'])
def verify():
    response = {"success": 0, "message": "An error has occurred. Please try again.."}
    try:
        if request.method == 'GET':
            email_id = request.args.get('email_id')
            code = request.args.get('code')
            db = mysql.connector.connect(
                host="merchantaliabbas.mysql.pythonanywhere-services.com",
                user="merchantaliabbas",
                passwd="rootroot",
                database="merchantaliabbas$betaNet"
            )

            db_cursor = db.cursor()
            db_cursor.execute("SELECT * FROM {} WHERE {} = '{}';".
                              format(user_table, 'email_id', email_id))
            data = db_cursor.fetchone()
            if data is None:
                return "Please register first."
            else:
                _id = data[0]
                random_number = data[5]
                if hasher.check_verification_hash(_id, email_id, code, random_number):
                    db_cursor.execute("UPDATE {} SET {} = {} WHERE {} = '{}';"
                                      .format(user_table, 'verified', 1, 'email_id', email_id))
                    db.commit()
                else:
                    return "Invalid verification link!"
                return "Your email-id has been successfully verified!"
        else:
            response["message"] = "Error accessing data"  # should not ideally happen
    except Exception as e:
        pass
    finally:
        try:
            db.close()
        except Exception as e:
            pass
    return jsonify(response)
