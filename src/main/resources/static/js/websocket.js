//var wsUri = "ws://localhost:8086/application/ws";// "ws://" + document.location.host + "/application/ws";
var wsUri = "wss://api.dev.iot.awsm.works/application/ws";
var websocket = new WebSocket(wsUri);

websocket.onmessage = function(evt) { onMessage(evt) };
websocket.onerror = function(evt) { onError(evt) };
websocket.onopen = function(evt) { onOpen(evt) };
websocket.onclose = function(evt) {onClose(evt)};

var interval;
/*
var optionsbli =
{
    chart: {

    },

    rangeSelector: {
        buttons: [{
            count: 1,
            type: 'minute',
            text: '1M'
        }, {
            count: 5,
            type: 'minute',
            text: '5M'
        }, {
            type: 'all',
            text: 'All'
        }],
            inputEnabled: false,
            selected: 0
    },

    title: {
        text: 'Live random data'
    },

    exporting: {
        enabled: false
    },

    series: [{
        name: 'Random data',
        data: [new Date().getTime(), 15]
    }]
};
*/
/*
var chart;

$(function () {

    Highcharts.setOptions({
        global: {
            useUTC: false
        }
    });

    // Create the chart
    chart = new Highcharts.StockChart({
        chart: {
            renderTo: 'container'
        },
        rangeSelector: {
            buttons: [{
                count: 1,
                type: 'minute',
                text: '1M'
            }, {
                count: 5,
                type: 'minute',
                text: '5M'
            }, {
                type: 'all',
                text: 'All'
            }],
            inputEnabled: false,
            selected: 0
        },

        title: {
            text: 'Live random data'
        },

        exporting: {
            enabled: false
        },
        series: [{
            data: (function () {
                // generate an array of random data
                var data = [],
                    time = (new Date()).getTime(),
                    i;

                for (i = -999; i <= 0; i += 1) {
                    data.push([
                        time + i * 1000,
                        1
                    ]);
                }
                return data;
            }())
        }]
    });

});
*/


function updateTemperature(temperature) {
    var x = (new Date()).getTime(); // current time
    var series = chart.series[0];
    if ($.isNumeric(temperature)) {
        var temp = parseFloat(temperature);
        series.addPoint([x, temp], true, true);
    } else
    {
        console.log("Temperature is not a number: " + temperature);
    }
}


function onMessage(evt) {
    //console.log("received over websockets: " + evt.data);
    try {

        var data = JSON.parse(evt.data);
        var payload = data.payload;

        //addToGraph(payload);
        updatePresentation(payload);
        writeToScreen(evt.data);

    } catch (e){
        console.log(e);
    }
}


function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function onOpen() {
    writeToScreen("<b>Connected</b> to " + wsUri);
    websocket.send("subscribe device baardlTI");
    websocket.send("subscribe device 4d6e848e-d99e-40c7-9c61-1744c525bfd8-de44fce1-ecb3-483d-ac22-113a3269bfc1");
    interval= setInterval(wsPing,1000);
}
function onClose(evt) {
    writeToScreen("<b>Disconnected</b> from " + wsUri);
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function wsPing(){
    websocket.send("ping");
}


// For testing purposes
var output = document.getElementById("output");
var grahdata = document.getElementById("grap");

function addToGraph(payload) {
    if (grahdata==null)
    {grahdata = document.getElementById("graph");}
    //output.innerHTML += message + "<br>";
    var graphText = "Temperature: <b>" + payload.temperature + "</b>";
    grahdata.innerHTML = graphText + "<br>";
    updateTemperature(payload.temperature);
}

function writeToScreen(message) {
    if (output==null)
    {output = document.getElementById("output");}
    //output.innerHTML += message + "<br>";
    output.innerHTML = message + "<br>";
}

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
}

