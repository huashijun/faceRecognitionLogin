import base64
import json

import face_recognition
from django.http import HttpResponse

# Create your views here.


def face_image_check():
    known_image = face_recognition.load_image_file("user_image.jpg")
    unknown_image = face_recognition.load_image_file("face_image.jpg")

    biden_encoding = face_recognition.face_encodings(known_image)[0]
    unknown_encoding = face_recognition.face_encodings(unknown_image)[0]

    results = face_recognition.compare_faces([biden_encoding], unknown_encoding)
    return HttpResponse(results)


def base64_to_image(image, image_name):
    imgdata = base64.b64decode(image)
    file = open(image_name, 'wb')
    file.write(imgdata)
    file.close()


def face_recognition_check(request):
    postBody = request.body
    json_result = json.loads(postBody)

    user_image = json_result['user_image']
    face_image = json_result['face_image']
    base64_to_image(user_image, 'user_image.jpg')
    base64_to_image(face_image, 'face_image.jpg')
    return face_image_check()

