import base64
from email.mime.text import MIMEText
from googleapiclient import errors
from googleapiclient.discovery import build
from httplib2 import Http
from oauth2client import file, client, tools

# If modifying these scopes, delete the file token.json.
SCOPES = 'https://www.googleapis.com/auth/gmail.send'


def create_message(sender, to, subject, message_text):
    message = MIMEText(message_text)
    message['to'] = to
    message['from'] = sender
    message['subject'] = subject
    b64_bytes = base64.urlsafe_b64encode(message.as_bytes())
    b64_string = b64_bytes.decode()
    return {'raw': b64_string}


def send_message(service, user_id, message):
    try:
        message = (service.users().messages().send(userId=user_id, body=message)
                   .execute())
        print('Message Id: %s' % message['id'])
        return message
    except errors.HttpError as error:
        print('An error occurred: %s' % error)


def send_verification_mail(email_id, code, send=True):
    if send:
        store = file.Storage('token.json')
        credentials = store.get()
        if not credentials or credentials.invalid:
            flow = client.flow_from_clientsecrets('credentials.json', SCOPES)
            credentials = tools.run_flow(flow, store)
        service = build('gmail', 'v1', http=credentials.authorize(Http()))
        # https: // stackoverflow.com / questions / 882712 / sending - html - email - using - python
        body = "Hey there!\nPlease verify your email-id, by clicking on this link: " \
               "http://merchantaliabbas.pythonanywhere.com/verify?email_id={}&code={}".format(email_id, code)
        # body = "Hey there!\nPlease verify your email-id, by clicking on this link: " \
        #        "http://127.0.0.1:5000/verify?email_id={}&code={}".format(email_id, code)
        message = create_message('me', email_id, 'Email Verification for BetaNet', body)
        send_message(service, 'me', message)
        print("Email sent to:", email_id)
    else:
        print("Email not sent")
