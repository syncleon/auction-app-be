package com.inhouse.yoursell.controller

import com.inhouse.yoursell.dto.CreateAuctionDto
import com.inhouse.yoursell.dto.RestartAuctionDto
import com.inhouse.yoursell.exceptions.NotFoundException
import com.inhouse.yoursell.service.AuctionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.util.*


@RestController
@RequestMapping("/api/v1/auctions")
class AuctionController(private val auctionService: AuctionService) {

    @PostMapping
    fun createAuction(
        authentication: Authentication,
        @RequestBody payload: CreateAuctionDto
    ): ResponseEntity<Any> {
        return try {
            val createdAuction = auctionService.createAuction(authentication, payload)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAuction.id)
                .toUri()
            ResponseEntity.created(location).body("Auction created: $createdAuction")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }

    @GetMapping
    fun getAuctions(): ResponseEntity<Any> {
        return try {
            val response = auctionService.findAll()
            ResponseEntity.ok().body(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): ResponseEntity<Any> {
        return try {
            val response = auctionService.findById(id)
            ResponseEntity.ok().body(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
        }
    }

    @PutMapping("/restart")
    fun restartAuction(
        authentication: Authentication,
        @RequestBody payload: RestartAuctionDto
    ) : ResponseEntity<Any> {
        return try {
            val restartedAuction = auctionService.restartClosedAuctionById(authentication, payload)
            val location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(restartedAuction.id)
                .toUri()
            ResponseEntity.created(location).body("Auction restarted: $restartedAuction")
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(e.message)
        }
    }
}
