#!/bin/bash
java -jar trang.jar devices.xml devices.xml devices.xsd
xjc devices.xsd -p xml
cp xml/* ../src/xml/
cp devices.xml ../
