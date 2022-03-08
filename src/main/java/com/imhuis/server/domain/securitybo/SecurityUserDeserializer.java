package com.imhuis.server.domain.securitybo;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Set;

/**
 * @author: imhuis
 * @date: 2022/3/4
 * @description:
 */
public class SecurityUserDeserializer extends JsonDeserializer<SecurityUser> {

    /**
     * This method will create {@link SecurityUser} object. It will ensure successful object creation even if password key is null in
     * serialized json, because credentials may be removed from the {@link SecurityUser} by invoking {@link SecurityUser#eraseCredentials()}.
     * In that case there won't be any password key in serialized json.
     *
     * @param jp the JsonParser
     * @param ctxt the DeserializationContext
     * @return the user
     * @throws IOException if a exception during IO occurs
     * @throws JsonProcessingException if an error during JSON processing occurs
     */
    @Override
    public SecurityUser deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode jsonNode = mapper.readTree(jp);
        Set<? extends GrantedAuthority> authorities =
                mapper.convertValue(
                        jsonNode.get("authorities"),
                        new TypeReference<Set<SimpleGrantedAuthority>>() {}
                );
        JsonNode password = readJsonNode(jsonNode, "password");
        SecurityUser result =  new SecurityUser(
                readJsonNode(jsonNode, "username").asText(""),
                password.asText(),
                readJsonNode(jsonNode, "enabled").asBoolean(true),
                readJsonNode(jsonNode, "accountNonExpired").asBoolean(true),
                readJsonNode(jsonNode, "credentialsNonExpired").asBoolean(true),
                readJsonNode(jsonNode, "accountNonLocked").asBoolean(true),
                authorities,
                readJsonNode(jsonNode, "uid").asText(""),
                readJsonNode(jsonNode, "phone").asText(""),
                readJsonNode(jsonNode, "email").asText("")
        );

        if (password.asText(null) == null) {
            result.eraseCredentials();
        }
        return result;
    }

    private JsonNode readJsonNode(JsonNode jsonNode, String field) {
        return jsonNode.has(field) ? jsonNode.get(field) : MissingNode.getInstance();
    }

}
