package com.govuln.deserialization_my;

import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.map.LazyMap;
import java.util.HashMap;
import java.util.Map;

public class LazyMapExample {
    public static void main(String[] args) {
        // 创建一个普通的HashMap
        Map<String, Integer> hashMap = new HashMap<>();

        // 创建一个Transformer对象，用于在需要时生成Map中缺失键所对应的值
        Transformer transformer = new Transformer() {
            @Override
            public Integer transform(Object input) {
                // 在这个简单的示例中，我们简单地返回键的长度作为值
                return input.toString().length();
            }
        };

        // 使用LazyMap.decorate()方法创建一个惰性加载的Map
        Map lazyMap = LazyMap.decorate(hashMap, transformer);

        // 现在，尝试获取一个不存在的键对应的值
        String key = "example";
        System.out.println("The value for key '" + key + "' is: " + lazyMap.get(key)); // 输出：The value for key 'example' is: 7
    }
}
