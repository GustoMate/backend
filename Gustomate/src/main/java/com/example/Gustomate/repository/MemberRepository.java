package com.example.Gustomate.repository;

import com.example.Gustomate.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long>//어떤 Entity인지, pk 어떤 타입인지
{
    // 이메일로 회원 정보 조회( select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}