<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <title>sample result</title>
    
    <style>

        img {
            position: absolute;
            height: 40px;
            width: 100px;
            top: 13px;
            left: 5px;
        }

        input {
            position: absolute;
            left: 120px;
            top: 15px;
            width: 500px;
            height: 30px;
            font-size: 17pt;
        }

        button {
            position: absolute;
            left: 650px;
            top: 18px;
            width: 80px;
            height: 30px;
        }

        .content {
            position: relative;
            top: 50px;
            left: 120px;
            font-size: 20pt;
        }
        
        #linksblock { 
            position: relative;
            top: 60px;
            left: 120px;
            font-size: 20pt;
        }


    </style>
</head>
<body>  

        <div class="header">
        <form method = "post" action="googlesearchenginesimulator">
        <a href = "../index.html">
        <img alt="googlelogo" src="../res/googlelogo.png">
        </a>
            <input id = "inputbox" type = "text" name = "keywords">
        <button type="submit" height ="50px" width ="20px">Google</button>
        </form>
        </div>
        
        <div id = "linksblock" ng-app="" ng-init="${linkshere}">
            <a ng-repeat = "x in links" href = {{x.addr}}>{{x.title}}</p></a>
        </div>
              

        <%-- <div class="content">        
        <p><a href=${addr0}>${title0}</a></p>
        <p><a href=${addr1}>${title1}</a></p>
        <p><a href=${addr2}>${title2}</a></p>
        <p><a href=${addr3}>${title3}</a></p>
        <p><a href=${addr4}>${title4}</a></p>
        <p><a href=${addr5}>${title5}</a></p>
        <p><a href=${addr6}>${title6}</a></p>
        <p><a href=${addr7}>${title7}</a></p>
        <p><a href=${addr8}>${title8}</a></p>        
        <p><a href=${addr9}>${title9}</a></p>
        </div> --%>

</body>
</html>