import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Simple client that put()s and the get()s the data back from the distributed In-memory data store
 */
public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java Main put/get");
            System.exit(0);
        }


        // Get hold of the distributed cache defined within ehcache config xml
        // (defaults to ehcache.xml classpath resource)
        //==================================================================================
        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("distributedInMemoryStoreOne");

        if (args[0].equals("put")) {
            put(cache);
        } else {
            get(cache);
        }
        cacheManager.shutdown();
    }

    public static void put(Cache cache) {

        //=======================================================
        //put KeyValue(KV) pairs into distributed in-memory store
        //=======================================================
        for (int i = 0; i < 10; i++) {
            //construct key+value pair
            String key = "key-" + i;
            String value = "value-" + i;

            //push KV pair into BM
            //=====================
            cache.put(new Element(key, value));
            System.out.println("Put-ed [" + key + "," + value + "] into the distributed in-memory store i.e. TSA");
        }

    }

    public static void get(Cache cache) {
        //=======================================================
        // get KV pairs from distributed in-memory store
        //======================================================
        System.out.println("Got the following key back from BigMemory");
        for (int i = 0; i < 10; i++) {
            String key = "key-" + i;

            //get value from BM given the key
            //=================================
            Object value = cache.get(key).getObjectValue();


            System.out.println("Got  [" + key + "," + value + "] from the distributed in-memory store i.e. TSA");

        }
    }


}
