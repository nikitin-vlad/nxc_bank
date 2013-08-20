package org.common.conversion;

import java.util.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
 
public class MapBillsAdapter extends XmlAdapter<MapBillsAdapter.AdaptedMap, Map<Integer, Integer>> {
     
    public static class AdaptedMap {
        public List<Entry> entry = new ArrayList<Entry>();
    }
     
    public static class Entry {
        public Integer key;
        public Integer value;
    }
 
    public Map<Integer, Integer> unmarshal(AdaptedMap adaptedMap) throws Exception {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(Entry entry : adaptedMap.entry) {
            map.put(entry.key, entry.value);
        }
        return map;
    }
 
    public AdaptedMap marshal(Map<Integer, Integer> map) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for(Map.Entry<Integer, Integer> mapEntry : map.entrySet()) {
            Entry entry = new Entry();
            entry.key = mapEntry.getKey();
            entry.value = mapEntry.getValue();
            adaptedMap.entry.add(entry);
        }
        return adaptedMap;
    }
 
}
