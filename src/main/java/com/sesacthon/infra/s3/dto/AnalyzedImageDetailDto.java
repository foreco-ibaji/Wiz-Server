package com.sesacthon.infra.s3.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class AnalyzedImageDetailDto {

  private final String name;
  private final List<Integer> coordinate;
  private final Long id;

  public AnalyzedImageDetailDto(String name, List<Integer> coordinate, Long id) {
    this.name = name;
    this.coordinate = coordinate;
    this.id = id;
  }
}
