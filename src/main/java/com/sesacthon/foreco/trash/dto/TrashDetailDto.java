package com.sesacthon.foreco.trash.dto;


import com.sesacthon.foreco.category.entity.ViewType;
import com.sesacthon.foreco.disposal.dto.response.DisposalInfoDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrashDetailDto {

  private final Long id;
  private final ViewType detailType;
  private final String name;
  private final String disposalMethod;
  private final DisposalInfoDto disposalInfoDto;
  private final List<String> remark;
  private final String iconUrl;

  public TrashDetailDto(Long id, ViewType detailType, String name, String disposalMethod,
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
