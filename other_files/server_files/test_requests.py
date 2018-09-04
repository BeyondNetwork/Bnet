import requests

# # For Registration
ENDPOINT = "http://127.0.0.1:5000/register"
ENDPOINT = "http://merchantaliabbas.pythonanywhere.com/register"
data = {
    'user_name': 'test',
    'email_id': 'aliabbas9@outlook.com',
    'password_hash': 'some_hash'}

r = requests.post(url=ENDPOINT, data=data)
print(r.text)

# # For Login
# ENDPOINT = "http://127.0.0.1:5000/login"
# ENDPOINT = "http://merchantaliabbas.pythonanywhere.com/login"
# data = {
#     'email_id': 'aliabbas9@outlook.com',
#     'password_hash': 'some_hash'}
#
# r = requests.post(url=ENDPOINT, data=data)
# print(r.text)
