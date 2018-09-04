import hashlib
import file


def hash_for_verification(_id, email_id, random_number):
    rand_dig = hashlib.sha256(str(random_number).encode()).hexdigest()
    rand_email = hashlib.sha256(email_id.encode()).hexdigest()
    rand_id = hashlib.sha256(str(_id*file.mul).encode()).hexdigest()
    string = rand_id + rand_email + rand_dig + file.add
    hash_object = hashlib.sha256(string.encode())
    hex_dig = hash_object.hexdigest()
    for i in range(10**file.power+10**(file.power-1)-1):
        hash_object = hashlib.sha256(hex_dig.encode())
        hex_dig = hash_object.hexdigest()
    return str(hex_dig[0:15] + '0' + hex_dig[16:30] + '0' + hex_dig[31:45] + '0' + hex_dig[46:])


def check_verification_hash(_id, email_id, the_hash, random_number):
    return hash_for_verification(_id, email_id, random_number) == the_hash


# code = hash_for_verification(1, 'aa@bb.com', 55)
# print(code)
# print(check_verification_hash(1, 'aa@bb.com', code, 55))
