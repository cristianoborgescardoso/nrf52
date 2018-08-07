#!/bin/bash
java -jar trang.jar devices.xml devices.xsd

#replacing xs:integer to xs:int, xs:integer generating BigInteger in Java, it is not necessary in this case.
sed -i 's/xs:integer/xs:int/g' devices.xsd

xjc devices.xsd -p xml

#Overriding toString at Devices.xml, this is necessary to correct behavior of the ComboBox
sed  -i '$s/}.$/   @Override\n   public String toString()\n   {\n      return description;\n   }\n}/g' xml/DeviceXml.java

cp xml/* ../src/xml/
#cp devices.xml ../
