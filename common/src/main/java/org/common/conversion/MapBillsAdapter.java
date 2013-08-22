package org.common.conversion;

import java.util.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
 
public class MapBillsAdapter extends XmlAdapter<MapBillsAdapter.AdaptedMap, Map<Integer, Integer>> {
     
    public static class AdaptedMap {
        public List<Bill> bill = new ArrayList<Bill>();
    }
     
    public static class Bill {
        public Integer key;
        public Integer value;
    }
 
    public Map<Integer, Integer> unmarshal(AdaptedMap adaptedMap) throws Exception {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(Bill bill : adaptedMap.bill) {
            map.put(bill.key, bill.value);
        }
        return map;
    }
 
    public AdaptedMap marshal(Map<Integer, Integer> map) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for(Map.Entry<Integer, Integer> mapEntry : map.entrySet()) {
            Bill bill = new Bill();
            bill.key = mapEntry.getKey();
            bill.value = mapEntry.getValue();
            adaptedMap.bill.add(bill);
        }
        return adaptedMap;
    }
 
}
