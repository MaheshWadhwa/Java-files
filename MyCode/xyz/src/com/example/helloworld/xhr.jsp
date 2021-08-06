<!doctype html>
<html>
<head>
<title>AD RUM XHR Test123</title>
<meta charset="UTF-8">
<style type="text/css">
a, a:visited {
    color: blue;
    padding: 5px;
}
#result {
    padding: 5px;
}
</style>
<script type="text/javascript">
var errors = errors || [];
var logError = function(msg) {
    errors.push(msg);
};

window.onerror = function(msg) {
    logError(msg);
}

var myAddEventListener = function(eventName, callback) {
    if (window.attachEvent)
        window.attachEvent("on" + eventName, callback);
    else if (window.addEventListener)
        window.addEventListener(eventName, callback, false);
};
myAddEventListener("load", function() {
    logError = function(msg) {
        document.getElementById("errors").innerHTML = msg;
    }
    for (e in errors) {
        logError(e);
    }
});
</script>

<script charset='UTF-8'>
window['adrum-start-time'] = new Date().getTime();
(function(config){
    config.appKey = 'SH-AAB-AAD-PBM';
    config.adrumExtUrlHttp = 'http://cdn.appdynamics.com';
    config.adrumExtUrlHttps = 'https://cdn.appdynamics.com';
    config.beaconUrlHttp = 'http://shadow-eum-col.appdynamics.com';
    config.beaconUrlHttps = 'https://shadow-eum-col.appdynamics.com';
    config.xd = {enable : false};
})(window['adrum-config'] || (window['adrum-config'] = {}));
</script>
<script src='//cdn.appdynamics.com/adrum/adrum-4.5.11.2466.js'></script>


<script type="text/javascript">
    // Enable conditional Internet Explorer compilation.
    // This is done here to verify that we don't have issues like CORE-20335
    var ie =  /*@cc_on!@*/false;

    ADRUM.conf.viz = 'episodesviz';
</script>

<script>
function safe(func) {
    return function() {
        try {
            var args = Array.prototype.slice.call(arguments);
            func.apply(this, args);
        } catch (e) {
            document.getElementById("errors").innerHTML = ("ERROR: " + e);
        }
    };
}

function newXhr() {
    document.getElementById("result").innerHTML = ""; // clear result from previous test, if any
    if (window.XMLHttpRequest) {
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) { // Older IE.
        return new ActiveXObject("MSXML2.XMLHTTP.3.0");
    }
}

function withMultipleBTs() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
    if (xhr.readyState == 4) {
    document.getElementById("result").innerHTML = xhr.responseText;
    }
    });
    xhr.open("GET", "/multi-bt");
    xhr.send(null);
}

function withSplitBT() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            document.getElementById("result").innerHTML = xhr.responseText;
        }
    });
    xhr.open("POST", "/split-bt");
    xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xhr.send("bt=1");
    }


function withOrsc() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            document.getElementById("result").innerHTML = xhr.responseText;
        }
    });
    xhr.open("GET", "/xhr/uptime");
    xhr.send(null);
}

function withError() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            document.getElementById("result").innerHTML = xhr.responseText;
        }
    });
    xhr.open("GET", "/xhr/non-existent");
    xhr.send(null);
}

function withErrorNonText() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            document.getElementById("result").innerHTML = typeof xhr.response != 'undefined' ? ""+xhr.response : xhr.responseText;
        }
    });
    xhr.open("GET", "/xhr/non-existent");
    xhr.responseType = 'json';
    xhr.send();
}

function setOrscAfterSend() {
    var xhr = newXhr();
    xhr.open("GET", "/xhr/uptime");
    xhr.send(null);
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            document.getElementById("result").innerHTML = xhr.responseText;
        }
    });
}

function withEventListener() {
    var xhr = newXhr();
    if (xhr.addEventListener) {
        xhr.open("GET", "/xhr/uptime");
        xhr.addEventListener("load", safe(function() {
            document.getElementById("result").innerHTML = xhr.responseText;
        }), false);
        xhr.send(null);
    } else {
        document.getElementById("result").innerHTML = "PASS: (" + new Date().getTime() + ") no addEventListener on this browser";
    }
}

function withTwoEventListeners() {
    var xhr = newXhr();
    if (xhr.addEventListener) {
        xhr.open("GET", "/xhr/uptime");
        var callback = safe(function() {
            if (typeof(console) !== "undefined" && console !== null && console.log)
                console.log("called back");
            document.getElementById("result").innerHTML = xhr.responseText;
        });
        xhr.addEventListener("load", callback, false);
        xhr.removeEventListener("error", callback, false);
        xhr.removeEventListener("load", callback, false);
        xhr.send(null);
    } else {
        document.getElementById("result").innerHTML = "PASS: (" + new Date().getTime() + ") no addEventListener on this browser";
    }
}

function noListener() {
    // We fire this XHR without any associated listeners. We try for a
    // bit to wrap a listener, and when we fail we set a watcher for this XHR.
    // When this XHR returns we capture its metrics.
    //
    // We should test here that the uptime is not updated, but the metrics
    // are captured.
    var xhr = newXhr();
    xhr.open("GET", "/xhr/uptime");
    xhr.send(null);
}

function timeout() {
    // This XHR takes 40s on the server-side. We fire the XHR without any
    // associated listeners. We try for a bit to wrap a listener, and when
    // we fail we set a watcher for this XHR. 10s after firing the XHR,
    // the code does attach an osrc callback which will update the uptime
    // when the long XHR returns.
    // By then, after 30s of watching, our code will abandon this XHR - so
    // no metric will be reported.
    //
    // We should test here that the uptime gets updated, but the metrics are
    // not captured.
    var xhr = newXhr();
    xhr.open("GET", "/xhr/long");
    xhr.send(null);
    setTimeout(safe(function() {
        xhr.onreadystatechange = function() {
            if (xhr.readyState == 4) {
                document.getElementById("result").innerHTML = xhr.responseText;
            }
        };
    }), 10000);
}

function waitFor(nSecs) {
    var until = new Date().getTime() + (nSecs * 1000);
    do { /* nothing */ } while (new Date().getTime() < until);
}

function withShortOrscAndLongListener() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            if (typeof(console) !== "undefined" && console !== null && console.log)
                console.log("short orsc");
            document.getElementById("result").innerHTML = xhr.responseText;
        }
    });
    if (xhr.addEventListener) {
        xhr.addEventListener("load", safe(function() {
            if (typeof(console) !== "undefined" && console !== null && console.log)
                console.log("long listener");
            waitFor(5);
            document.getElementById("result").innerHTML = xhr.responseText;
        }), false);
    }
    xhr.open("GET", "/xhr/uptime");
    xhr.send(null);
}

function withLongOrscAndShortListener() {
    var xhr = newXhr();
    xhr.onreadystatechange = safe(function() {
        if (xhr.readyState == 4) {
            if (typeof(console) !== "undefined" && console !== null && console.log)
                console.log("long orsc");
            waitFor(5);
            document.getElementById("result").innerHTML = xhr.responseText;
        }
    });
    if (xhr.addEventListener) {
        xhr.addEventListener("load", safe(function() {
            if (typeof(console) !== "undefined" && console !== null && console.log)
                console.log("short listener");
            document.getElementById("result").innerHTML = xhr.responseText;
        }), false);
    }
    xhr.open("GET", "/xhr/uptime");
    xhr.send(null);
}

function reuseXHR() {
   var xhr = newXhr();
   xhr.open("GET", "/xhr/uptime");
   xhr.onreadystatechange = safe(function() {
       if (xhr.readyState == 4) {
         document.getElementById("result").innerHTML = xhr.responseText;
         xhr.open("GET", "/multi-bt");
         xhr.onreadystatechange = safe(function() {
              if (xhr.readyState == 4) {
                  if (typeof(console) !== "undefined" && console !== null && console.log)
                      console.log("onreadystatechange for xhr2: " + xhr.responseText);

                  document.getElementById("result").innerHTML = xhr.responseText;

                  //reuse xhr again
                  xhr.open("GET", "/xhr/non-existent");
                  xhr.onreadystatechange = safe(function() {
                    console.log("/xhr/non-existent responded.");
                  });
                  xhr.send(null);
              }
         });

         xhr.send(null);
      }
   });
   xhr.send(null);
}

function reuseXHRBeforeReady() {
   var xhr = newXhr();
   xhr.open("GET", "/xhr/uptime");
   /*if (xhr.addEventListener) {
       xhr.addEventListener("load", safe(function() {
           if (typeof(console) !== "undefined" && console !== null && console.log)
               console.log("load event on xhr1: " + xhr.responseText);
           document.getElementById("result").innerHTML = xhr.responseText;

       }), false);
   }*/
   xhr.onreadystatechange = safe(function() {
          if (xhr.readyState == 4) {
                if (typeof(console) !== "undefined" && console !== null && console.log)
                    console.log("onreadystatechange for xh1: " + xhr.responseText);

                document.getElementById("result").innerHTML = xhr.responseText;
          }
      });
   xhr.send(null);

   xhr.open("GET", "/multi-bt");
   xhr.onreadystatechange = safe(function() {
       if (xhr.readyState == 4) {
             if (typeof(console) !== "undefined" && console !== null && console.log)
                 console.log("onreadystatechange for xhr2: " + xhr.responseText);

             document.getElementById("result").innerHTML = xhr.responseText;
       }
   });

    xhr.send(null);
}
</script>
</head>
<body>
<h1>xhr.jsp</h1>

<div id="errors"></div>
<div id="result"></div>

<p>Get Server Uptime by XHR:</p>
<ul>
    <li><a href="" onclick="safe(withOrsc)(); return false;">With ORSC</a></li>
    <li><a href="" onclick="safe(withError)(); return false;">With error</a></li>
    <li><a href="" onclick="safe(withErrorNonText)(); return false;">With error and non-text responseType</a></li>
    <li><a href="" onclick="safe(setOrscAfterSend)(); return false;">Setting ORSC After Send</a></li>
    <li><a href="" onclick="safe(withEventListener)(); return false;">With Event Listener</a></li>
    <li><a href="" onclick="safe(withTwoEventListeners)(); return false;">With Two Event Listeners</a></li>
    <li>
        With ORSC &amp; Event Listener where:
        <ul>
            <li><a href="" onclick="safe(withShortOrscAndLongListener)(); return false;">Event Listener takes time</a></li>
            <li><a href="" onclick="safe(withLongOrscAndShortListener)(); return false;">ORSC takes time</a></li>
        </ul>
    </li>
    <li>
        With neither ORSC nor Event Listener where:
        <ul>
            <li><a href="" onclick="safe(noListener)(); return false;">XHR returns soon</a></li>
            <li><a href="" onclick="safe(timeout)(); return false;">XHR takes too long</a></li>
        </ul>
    </li>
    <li><a href="" onclick="safe(withMultipleBTs)(); return false;">With Multiple BTs</a></li>
    <li><a href="" onclick="safe(withSplitBT)(); return false;">With Split BT</a></li>
    <li><a href="" onclick="safe(reuseXHR)(); return false;">Test reuse XHR Object</a></li>
    <li><a href="" onclick="safe(reuseXHRBeforeReady)(); return false;">Test a case of reuse XHR Object before last request finishes</a></li>
</ul>
<a id="reload" href="xhr.jsp">Reload</a>

<p>Metrics collected:</p>
<pre id="episodesviz">
</pre>
<script type="text/javascript">
   ADRUM.command("setPageName", "hi i am xhr  page1");
</script>
</body>
</html>
