/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Cristiano
 */
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import xml.DeviceXmls;

public class Teste
{

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, JAXBException
    {
//       System.out.println(unm2().getPeriodoXml.get(1).getSigla());
        System.out.println(unm2().getInputDevicesXml().getDeviceXml().get(4).getDescription());
    }

    public static DeviceXmls unm2() throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(DeviceXmls.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

//       StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>    <periodoXmls>        <periodoXml>            <inicio>2012-02-28T00:00:00-03:00</inicio>            <fim>2012-07-08T00:00:00-03:00</fim>            <sigla>2012s01</sigla>        </periodoXml>        <periodoXml>            <inicio>2012-07-31T00:00:00-03:00</inicio>            <fim>2012-12-10T00:00:00-02:00</fim>            <sigla>2012s02</sigla>        </periodoXml>    </periodoXmls> ");
        StringReader reader = new StringReader("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                + "<deviceXmls>   \n"
                + "	<inputDevicesXml> \n"
                + "		<deviceXml type=\"sensor\">  \n"
                + "			<name>PIR</name> \n"
                + "			<description>Passive infra-red sensor</description>  \n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml> \n"
                + "		<deviceXml type=\"sensor\">  \n"
                + "			<name>Photo</name> \n"
                + "			<description>Photo light sensitive resistor</description>\n"
                + "			<ioClass>input</ioClass> 		\n"
                + "		</deviceXml> \n"
                + "		<deviceXml>  \n"
                + "			<name>Sound</name> \n"
                + "			<description>Sound sensor</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml> \n"
                + "		<deviceXml>  \n"
                + "			<name>Pulse</name> \n"
                + "			<description>Heart rate pulse sensor</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  \n"
                + "			<name>Pulse</name> \n"
                + "			<description>Heart rate pulse sensor</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  \n"
                + "			<name>Laser</name> \n"
                + "			<description>Laser sensor module</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  	    \n"
                + "			<name>UDS</name> \n"
                + "			<description>Ultrasonic distance Sensor</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  	    \n"
                + "			<name>Temp</name> \n"
                + "			<description>Temperature sensor module</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  	    \n"
                + "			<name>Tilt</name> \n"
                + "			<description>Tilt switch module</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  	    \n"
                + "			<name>TempH</name> \n"
                + "			<description>Temperature and humidity sensor module</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>  	    \n"
                + "			<name>Rot</name> \n"
                + "			<description>Rotary encoder module</description>\n"
                + "			<ioClass>input</ioClass>		\n"
                + "		</deviceXml>\n"
                + "	</inputDevicesXml>\n"
                + "	<outputDevicesXml>\n"
                + "		<deviceXml>\n"
                + "			<name>Re</name> \n"
                + "			<description>5V Relay Module</description>\n"
                + "			<ioClass>output</ioClass>		\n"
                + "		</deviceXml>\n"
                + "		<deviceXml>\n"
                + "			<name>buzz</name> \n"
                + "			<description>Active buzzer module</description>\n"
                + "			<ioClass>output</ioClass>		\n"
                + "		</deviceXml> \n"
                + "	</outputDevicesXml>\n"
                + "</deviceXmls> ");
        DeviceXmls periodos = (DeviceXmls) unmarshaller.unmarshal(new File("devices.xml"));
//        DeviceXmls periodos = (DeviceXmls) unmarshaller.unmarshal(reader);
        return periodos;
    }
}
