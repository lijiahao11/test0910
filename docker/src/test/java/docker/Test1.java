package docker;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class Test1 {

	@Test
	public void test1() {
		Jedis j = new Jedis("192.168.233.150",7000);
		j.set("key1", "value1");
		String v = j.get("key1");
		System.out.println(v);
		
		j.close();
	}
}
