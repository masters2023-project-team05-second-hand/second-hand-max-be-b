package kr.codesquad.secondhand.api.chat.controller;

import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractMemberId;

import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.chat.dto.ChatRoomCreateDto;
import kr.codesquad.secondhand.api.chat.dto.reponse.ChatRoomExistenceCheckResponse;
import kr.codesquad.secondhand.api.chat.service.ChatFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatFacadeService chatService;

    @PostMapping("/api/chat/room")
    public ResponseEntity<ChatRoomCreateDto.Response> createChatRoom(HttpServletRequest httpServletRequest,
                                                                     @Validated @RequestBody ChatRoomCreateDto.Request request) {
        Long memberId = extractMemberId(httpServletRequest);
        ChatRoomCreateDto.Response response = chatService.createChatRoom(memberId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/api/chat-room/{productId}")
    public ResponseEntity<ChatRoomExistenceCheckResponse> checkChatRoomExistence(HttpServletRequest httpServletRequest,
                                                                                 @PathVariable Long productId) {
        Long memberId = extractMemberId(httpServletRequest);
        ChatRoomExistenceCheckResponse response = chatService.checkChatRoomExistence(memberId, productId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
