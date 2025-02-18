package com.packman.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    //고유 uuid
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //공통식별자
    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String providerId;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column
    private String name;
    
    @Column
    private String nickname;
    
    @Column
    private String profileImage;

    @Column
    private String birthday;

    @Column
    private String gender;

    @Column
    private String ageRange;

}
