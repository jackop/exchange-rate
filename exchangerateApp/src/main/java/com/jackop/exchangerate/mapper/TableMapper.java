package com.jackop.exchangerate.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jackop.exchangerate.models.Table;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TableMapper {

  private static final Logger LOGGER = Logger.getLogger(TableMapper.class.getName());
  private static final ObjectMapper mapper = new ObjectMapper();

  public List<Table> map(String response) {
    List<Table> tableList = null;
    try {
      JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, Table.class);
      tableList = mapper.readValue(response, type);
    } catch (IOException e) {
      LOGGER.warning("map | Table | Exception: " + e.getMessage());
    }

    return tableList;
  }
}
