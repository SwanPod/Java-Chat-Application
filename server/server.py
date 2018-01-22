import socket
import threading
import datetime
import time

connections = set()

#Change to your server's IP address and correct port (Don't use port 80, that is for web pages)
ip =  '127.0.0.1'
port = 80

print('Server started on port', port)

def send_all(message):
    for connection in connections:
        ts = time.time()
        st = datetime.datetime.fromtimestamp(ts).strftime('%H:%M:%S ')
        message1 = message.decode('utf8')
        message2 = st + message1
        message3 = message2.encode('ascii')
        print ("Sending %s" % message3)
        connection.send(message3)


def receive(connection):
    while True:
        print ("Waiting for message")
        message = connection.recv(1024)
        if not message:
            print ("Closing connection and removing from registry")
            connections.remove(connection)
            return
        print ("Received %s, sending to all" % message)
        send_all(message)


#set up the listening socket
sckt = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sckt.bind((ip, port))
sckt.listen(10)

#accept connections in a loop
while True:
    print ("Waiting for a connection")
    (connection, address) = sckt.accept()#accepts connection
    print ("Connection received. Adding to registry")
    connections.add(connection)
    print ("Spawning receiver")
    threading.Thread(target = receive, args=[connection]).start()
