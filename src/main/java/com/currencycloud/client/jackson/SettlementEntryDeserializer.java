package com.currencycloud.client.jackson;

import com.currencycloud.client.model.Settlement;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SettlementEntryDeserializer extends JsonDeserializer<Map<String, Settlement.Entry>> {

    @Override
    public Map<String, Settlement.Entry> deserialize(JsonParser jp, DeserializationContext context)
            throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        List<Map<String, Settlement.Entry>> maps = mapper.readValue(jp, new TypeReference<List<Map<String, Settlement.Entry>>>() { });
        Map<String, Settlement.Entry> mergedMap = new LinkedHashMap<>();
        for (Map<String, Settlement.Entry> map : maps) {
            mergedMap.putAll(map);
        }
        return mergedMap;
    }
}
