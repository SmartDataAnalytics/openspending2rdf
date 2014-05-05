package org.aksw.linkedspending;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.eclipse.jdt.annotation.Nullable;

import java.io.IOException;

/**
* ResultsReader
*
*/
public class ResultsReader
{
    final protected JsonParser jp;

    public ResultsReader(String datasetName) throws IOException
    {
        JsonFactory f = new MappingJsonFactory();
        jp = f.createParser(JsonDownloader.getFile(datasetName));
        JsonToken current = jp.nextToken();
        if (current != JsonToken.START_OBJECT) {
            System.out.println();
            throw new IOException("Error with dataset "+datasetName+": root should be object: quiting.");
        }
        while (!"results".equals(jp.getCurrentName())) {jp.nextToken();}
        if (jp.nextToken() != JsonToken.START_ARRAY)
        {throw new IOException("Error with dataset "+datasetName+": array expected.");}
    }

    @Nullable
    public JsonNode read() throws IOException
    {
        if(jp.nextToken() == JsonToken.END_ARRAY) {jp.close();return null;}
        return jp.readValueAsTree();
    }
}
