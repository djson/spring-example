<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/el/include_header.jsp" %>
<!DOCTYPE html>
<html lang=ko>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1">
    </head>
    <body>
        <tiles:insertAttribute name="CONTENT"/>
    </body>
</html>