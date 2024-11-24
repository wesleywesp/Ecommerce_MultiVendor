package com.wesp.request;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
public record WishlistRequestDTO(
                                 @NotNull(message = "O usuário é obrigatório")
                                 Long userId, // ID do usuário associado à wishlist

                                 @NotNull(message = "A lista de produtos não pode ser nula")
                                 Set<Long> productIds // IDs dos produtos associados à wishlist
) {
}
