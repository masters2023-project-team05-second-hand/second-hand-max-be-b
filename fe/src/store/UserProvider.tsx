import { useUserInfoQuery } from "@api/queries";
import { useToast } from "@hooks/useToast";
import React, { useEffect } from "react";
import { useSetAddresses, useSetMember } from "store";
export default function UserProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const accessToken = localStorage.getItem("accessToken");

  const setMember = useSetMember();
  const setAddresses = useSetAddresses();

  const { toast } = useToast();

  const [memberResult, memberAddressResult] = useUserInfoQuery({
    enabled: !!accessToken,
  });

  useEffect(() => {
    if (memberResult.isSuccess) {
      setMember(memberResult.data);
    }
    if (memberResult.isError) {
      toast({
        type: "error",
        title: "유저 정보 조회 실패",
        message: "유저 정보를 조회하는데 실패했습니다. 새로고침 해주세요.",
      });
    }
  }, [memberResult, setMember, toast]);

  useEffect(() => {
    if (memberAddressResult.isSuccess) {
      const userAddressesInfo = memberAddressResult.data;
      setAddresses(
        userAddressesInfo.map((address) => ({
          id: address.id,
          name: address.name,
        }))
      );
    }
    if (memberAddressResult.isError) {
      toast({
        type: "error",
        title: "유저 주소 조회 실패",
        message: "유저 주소를 조회하는데 실패했습니다. 새로고침 해주세요.",
      });
    }
  }, [memberAddressResult, setAddresses, toast]);

  return children;
}
