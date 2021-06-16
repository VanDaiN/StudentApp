// Import module
var express = require('express');
var http = require('http');
//var username = '';

// Create Server
var app = express();
var server = http.createServer(app);

// Set port
server.listen(8080);
console.log("Node.js Server đang chạy trên cổng 8080");

// Routes HTTP GET Request /localhost:8080/
app.get('/', function(req, res) {
    res.sendfile('index.html');
});

app.get('/about', function(req, res) {
    res.sendfile('about.html');
});
app.get('/tintuc', function(req, res) {
    res.sendfile('tintuc.html');
});

//Routes HTTP POST Request
app.post('/about', function(req, res) {
    if (req.method === 'POST') {
        var data = '';

        req.on('data', function(chunk) {
            data += chunk;
        });

        req.on('end', function() {
            var values = data.split("&");
            var user = values[0].split("=");
            username = user[1];
            var pass = values[1].split("=");
            var password = pass[1];

            console.log(data);
            console.log("User =" + username);
            console.log("Pass =" + password);
            
        });
    }


});

















// // response.writeHead(200, {"Content-Type": "text/html"});
// //     response.write("<!DOCTYPE html>");
// //     response.write("<html>");
// //     response.write("<head>");
// //     response.write("<meta charset='utf-8'>");
// //     response.write("<title>Hello World Page</title>");
// //     response.write("</head>");
// //     response.write("<body>");
// //     response.write("Xin chào");
// //     response.write("</body>");
// //     response.write("</html>");
// //     response.end();