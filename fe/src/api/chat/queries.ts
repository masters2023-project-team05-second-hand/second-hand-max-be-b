import { chatKeys } from "@api/queryKeys";
import { useToast } from "@hooks/useToast";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  deleteChatRoom,
  getChatDetail,
  getChatList,
  getChatRoomId,
  postNewChatRoom,
} from ".";

export const useMakeRoomMutation = ({
  productId,
  callback,
}: {
  productId: number;
  callback: ({
    roomId,
    sentTime,
    message,
  }: {
    roomId: string;
    sentTime: string;
    message: string;
  }) => void;
}) => {
  const { toast } = useToast();

  const postNewChatRoomMutation = useMutation(postNewChatRoom);
  const onPostNewChatRoom = (message: string) => {
    postNewChatRoomMutation.mutate(
      { productId, message },
      {
        onSuccess: ({ roomId, sentTime }) => {
          callback({ roomId, sentTime, message });
        },
        onError: () => {
          toast({
            type: "error",
            title: "채팅방 생성 실패",
            message: "채팅방 생성에 실패했습니다. 잠시 후 다시 시도해주세요.",
          });
        },
      }
    );
  };

  return { onPostNewChatRoom };
};

export const useGetChatDetailQuery = (roomId: string) => {
  return useQuery({
    ...chatKeys.chatDetail(roomId),
    queryFn: () => getChatDetail(roomId),
    enabled: !!roomId,
  });
};

export const useDeleteChatRoom = ({ productId }: { productId: number }) => {
  const { toast } = useToast();
  const queryClient = useQueryClient();

  const deleteChatRoomMutation = useMutation(deleteChatRoom);

  const onDeleteChatRoom = (roomId: string) => {
    deleteChatRoomMutation.mutate(roomId, {
      onSuccess: () => {
        queryClient.invalidateQueries(chatKeys.chatList(productId));
      },
      onError: () => {
        toast({
          type: "error",
          title: "채팅방 나가기 실패",
          message: "채팅방 나가기를 실패했어요. 잠시 후 다시 시도해주세요.",
        });
      },
    });
  };

  return { onDeleteChatRoom };
};

export const useGetChatListQuery = (productId?: number) => {
  return useQuery({
    ...chatKeys.chatList(productId),
    queryFn: () => getChatList(productId),
  });
};

export const useGetChatRoomId = ({
  productId,
  onSuccess,
}: {
  productId: number;
  onSuccess: (roomId: string) => void;
}) => {
  const { toast } = useToast();
  const { mutate } = useMutation(getChatRoomId);

  const onGetChatRoomId = () => {
    mutate(productId, {
      onSuccess: ({ roomId }) => {
        onSuccess(roomId);
      },
      onError: () => {
        toast({
          type: "error",
          title: "채팅방 정보 조회 실패",
          message: "채팅방 정보 조회에 실패했어요. 잠시 후 다시 시도해주세요.",
        });
      },
    });
  };

  return { onGetChatRoomId };
};
