var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var mongodb = require('mongodb');

var MongoClient = mongodb.MongoClient;
var url = 'mongodb://localhost:27017/ASM';


MongoClient.connect(url, function(err, client) {
    if (err) {
        console.log('Unable to connect to the mongoDB server. Error:', err);
    } else {
        //HURRAY!! We are connected. :)
        let db = client.db('ASM');
        console.log('Connection established to', url);
        collection = db.collection('ASM');
    }
});


io.on('connection', function(socket) {
    socket.on('join', function(data) {
        console.log("EventJoin: " + data);
    });


    socket.on('getSach', function(msg) {
        console.log("Nhan lenh getSach: " + msg);

        var cursor = collection.find();

        cursor.each(function(err, doc) {
            if (doc != null) {
                var strings = JSON.parse(JSON.stringify(doc));
                console.log(strings);
                socket.emit('getSach', strings)
            } else if (doc == null) {
                console.log("Ket thuc getSach");
            }
        });
    });

    socket.on('insertSach', function(id, ten, theloai, tacgia, nxb) {
        console.log(ten + "insertSach");

        var sach = { id: id, ten: ten, theloai: theloai, tacgia: tacgia, nxb: nxb };

        collection.insert(sach, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('insertSach', false);
            } else {
                console.log('Inserted new sach ok');
                socket.emit('insertSach', true);
            }
        });
    });




});

http.listen(3000, function() {
    console.log('listening on *:3000');
});