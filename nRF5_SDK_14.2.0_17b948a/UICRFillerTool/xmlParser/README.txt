To update the XML structure:
1-  Do the necessary changes
2 - Run the script 'generateNewJavaClasses.sh'
3 - Copy the new generated java classes to the project folder
4 - Change the necessary code in the project
5 - Recompile the project
6 - Overrid toString in DeviceXml to return te attribute 'description', such as: 
   @Override
    public String toString()
    {
        return description;
    }
