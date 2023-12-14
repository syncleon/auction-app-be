package com.inhouse.yoursell.entity.user

import com.inhouse.yoursell.entity.BaseEntity
import com.inhouse.yoursell.entity.auction.Auction
import com.inhouse.yoursell.entity.auction.Bid
import com.inhouse.yoursell.entity.vehicle.Vehicle
import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "user_id_seq")
    @SequenceGenerator(
        name = "user_id_seq",
        allocationSize = 1)
    var id: Long = 0L,

    @Column(
        name = "username",
        nullable = false
    )
    var username: String = "",

    @Column(
        name = "password",
        nullable = false
    )
    var password: String = "",

    @Column(
        name = "email",
        nullable = false
    )
    var email: String = "",

    @ManyToMany(
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var userRoles: MutableSet<Role> = mutableSetOf(),

    @OneToMany(
        mappedBy = "seller",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    var vehicles: MutableList<Vehicle> = mutableListOf(),

    @OneToMany(
        mappedBy = "bidder",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY
    )
    var bids: List<Bid> = mutableListOf(),

    @OneToOne(
        mappedBy = "auctionCreator",
        cascade = [CascadeType.ALL],
        fetch = FetchType.LAZY)
    var auction: Auction? = null,


    ) : BaseEntity()