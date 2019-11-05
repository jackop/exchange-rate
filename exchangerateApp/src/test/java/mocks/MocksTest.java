package mocks;

import static org.junit.Assert.assertEquals;
import com.jackop.exchangerate.models.Table;
import org.junit.Test;
import utils.MocksGenerate;

public class MocksTest {

  private final MocksGenerate mocksGenerate = new MocksGenerate();

  @Test
  public void testTablesValue_isCorrect() {

    Table testTable = mocksGenerate.createTable();

    assertEquals("check how table is name", "table1", testTable.getTable());
    assertEquals("check effective date of table", "2019-10-05", testTable.getEffectiveDate());
    assertEquals("check trading date of table", "2019-10-06", testTable.getTradingDate());
    assertEquals("check number of table", "1", testTable.getNo());
  }
}
