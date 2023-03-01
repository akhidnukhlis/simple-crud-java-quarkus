package akhid.development.dto;

import javax.validation.constraints.*;

public class CakeRequestDto {
    @NotNull
    @Size(min = 5, max = 100)
    public String tittle;

    @Size(min = 10, max = 255)
    public String description;

    @Size(max = 5)
    public int rating;

    @Size(max = 255)
    public String image;
}
