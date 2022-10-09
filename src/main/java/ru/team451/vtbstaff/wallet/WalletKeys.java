package ru.team451.vtbstaff.wallet;

import lombok.*;

import javax.persistence.*;

@Data
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class WalletKeys {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String privateKey;
    private String publicKey;
}
