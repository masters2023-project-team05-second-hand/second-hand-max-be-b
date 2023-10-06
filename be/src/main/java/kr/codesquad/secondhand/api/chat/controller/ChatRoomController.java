package kr.codesquad.secondhand.api.chat.controller;

import static kr.codesquad.secondhand.global.util.HttpAuthorizationUtils.extractMemberId;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import kr.codesquad.secondhand.api.chat.dto.ChatRoomCreateDto;
import kr.codesquad.secondhand.api.chat.dto.response.ChatRoomExistenceCheckResponse;
import kr.codesquad.secondhand.api.chat.dto.response.ChatRoomMessagesReadResponse;
import kr.codesquad.secondhand.api.chat.dto.response.ChatRoomReadResponse;
import kr.codesquad.secondhand.api.chat.service.ChatRoomFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomFacadeService chatRoomFacadeService;

    @PostMapping("/api/chat-room")
    public ResponseEntity<ChatRoomCreateDto.Response> createChatRoom(HttpServletRequest httpServletRequest,
                                                                     @Validated @RequestBody ChatRoomCreateDto.Request request) {
        Long memberId = extractMemberId(httpServletRequest);
        ChatRoomCreateDto.Response response = chatRoomFacadeService.createChatRoom(memberId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/api/chat-room/{productId}")
    public ResponseEntity<ChatRoomExistenceCheckResponse> checkChatRoomExistence(HttpServletRequest httpServletRequest,
                                                                                 @PathVariable Long productId) {
        Long memberId = extractMemberId(httpServletRequest);
        ChatRoomExistenceCheckResponse response = chatRoomFacadeService.checkChatRoomExistence(memberId, productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/chat-room")
    public ResponseEntity<List<ChatRoomReadResponse>> readChatRooms(HttpServletRequest httpServletRequest,
                                                                    @RequestParam(required = false, defaultValue = "0") Long productId) {
        Long memberId = extractMemberId(httpServletRequest);
        List<ChatRoomReadResponse> response = chatRoomFacadeService.findAllChatRoomsBy(memberId, productId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/chat-room/messages")
    public ResponseEntity<List<ChatRoomMessagesReadResponse>> readChatRoomMessages(@RequestParam String roomId) {
        List<ChatRoomMessagesReadResponse> response = chatRoomFacadeService.findChatRoomMessagesBy(roomId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/chat-room/{roomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable String roomId) {
        chatRoomFacadeService.deleteChatRoomBy(roomId);
        return ResponseEntity.ok()
                .build();
    }
}
