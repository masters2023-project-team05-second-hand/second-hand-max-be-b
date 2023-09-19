package kr.codesquad.secondhand.api.member.domain;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "member:")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberStatLogRedis {

    @Id
    private Long id;
    private String viewedProductIds;
    private String wishedProductIds;

    public MemberStatLogRedis(Long id) {
        this.id = id;
    }

    @Builder
    public MemberStatLogRedis(Long id, String viewedProductIds, String wishedProductIds) {
        this.id = id;
        this.viewedProductIds = viewedProductIds;
        this.wishedProductIds = wishedProductIds;
    }

    public void setViewedProductIds(List<Long> viewedProductIds) {
        this.viewedProductIds = listToJsonArray(viewedProductIds);
    }

    public void setWishedProductIds(List<Long> wishedProductIds) {
        this.wishedProductIds = listToJsonArray(wishedProductIds);
    }

    public List<Long> getViewedProductIdList() {
        return jsonArrayToList(viewedProductIds);
    }

    public List<Long> getWishedProductList() {
        return jsonArrayToList(wishedProductIds);
    }

    public List<Long> jsonArrayToList(String jsonArray) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(jsonArray == null){
                return new ArrayList<>();
            }
            return objectMapper.readValue(jsonArray, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String listToJsonArray(List<Long> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
