package com.example.customersys.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * メール確認用のワンタイムコードを管理するエンティティ
 */
@Entity
@Table(name = "confirmation_token")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConfirmationToken {

    /** 確認トークンID（PK） */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    /** 対象のメールアドレス */
    @Column(nullable = false, length = 100)
    private String email;

    /** 6桁の確認コード */
    @Column(nullable = false, length = 6)
    private String code;

    /** 期限（発行から24時間以内） */
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    /** 使用済みフラグ（true: 使用済み） */
    @Column(nullable = false)
    private Boolean usedFlag = false;
}