package com.ecommerce.system.shopping_cart_service.model.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {

    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    private String name;

    @Size(max = 255, message = "Product description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Product price cannot be null")
    @Min(value = 0, message = "Product price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Quantity in stock cannot be null")
    @Min(value = 0, message = "Quantity in stock must be greater than or equal to 0")
    private Integer quantityInStock;

}
