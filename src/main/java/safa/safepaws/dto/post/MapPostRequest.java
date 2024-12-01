package safa.safepaws.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapPostRequest {
    private Coordinate southWest;
    private Coordinate northEast;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Coordinate {
        private Double latitude;
        private Double longitude;
    }
}
