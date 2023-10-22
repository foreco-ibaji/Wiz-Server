package com.sesacthon.foreco.mock.trash.dto;

import com.sesacthon.foreco.mock.trash.DetailType;
import java.util.List;
import lombok.Getter;

@Getter
public class TrashDetailDto {

  private final Long id;
  private final DetailType detailType;
  private final String name;
  private final String disposalMethod;
  private final DisposalInfoDto disposalInfoDto; //기존에 있는 DTO 사용!
  private final List<String> remark;
  private final String iconUrl;

  public TrashDetailDto(Long id, DetailType detailType, String name, String disposalMethod,
      DisposalInfoDto disposalInfoDto, List<String> remark, String iconUrl) {
    this.id = id;
    this.detailType = detailType;
    this.name = name;
    this.disposalMethod = disposalMethod;
    this.disposalInfoDto = disposalInfoDto;
    this.remark = remark;
    this.iconUrl = iconUrl;
  }
}