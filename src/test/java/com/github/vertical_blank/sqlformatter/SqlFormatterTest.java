package com.github.vertical_blank.sqlformatter;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SqlFormatterTest {

	@Test
	public void simple() {
		String format = SqlFormatter.format("SELECT foo, bar, CASE baz WHEN 'one' THEN 1 WHEN 'two' THEN 2 ELSE 3 END FROM table");
		assertEquals(format, "SELECT\n" +
						"  foo,\n" +
						"  bar,\n" +
						"  CASE\n" +
						"    baz\n" +
						"    WHEN 'one' THEN 1\n" +
						"    WHEN 'two' THEN 2\n" +
						"    ELSE 3\n" +
						"  END\n" +
						"FROM\n" +
						"  table");
	}

	@Test
	public void withIndent() {
		String format = SqlFormatter.format(
										"SELECT foo, bar, CASE baz WHEN 'one' THEN 1 WHEN 'two' THEN 2 ELSE 3 END FROM table",
										"    ");
		assertEquals(format, "SELECT\n" +
						"    foo,\n" +
						"    bar,\n" +
						"    CASE\n" +
						"        baz\n" +
						"        WHEN 'one' THEN 1\n" +
						"        WHEN 'two' THEN 2\n" +
						"        ELSE 3\n" +
						"    END\n" +
						"FROM\n" +
						"    table");
	}

	@Test
	public void withNamedParams() {
		Map<String, String> namedParams = new HashMap<>();
		namedParams.put("foo", "'bar'");

		String format = SqlFormatter.format("SELECT * FROM tbl WHERE foo = @foo", namedParams);
		assertEquals(format, "SELECT\n" +
						"  *\n" +
						"FROM\n" +
						"  tbl\n" +
						"WHERE\n" +
						"  foo = 'bar'");
	}

	@Test
	public void withIndexedParams() {
		String format = SqlFormatter.format("SELECT * FROM tbl WHERE foo = ?", Arrays.asList("'bar'"));
		assertEquals(format, "SELECT\n" +
						"  *\n" +
						"FROM\n" +
						"  tbl\n" +
						"WHERE\n" +
						"  foo = 'bar'");
	}

}