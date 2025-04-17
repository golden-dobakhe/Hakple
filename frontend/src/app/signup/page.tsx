"use client";

import { useState, ChangeEvent, FormEvent } from "react";
import Link from "next/link";
import Image from "next/image";

export default function Signup() {
  const [formData, setFormData] = useState({
    nickname: "",
    phone: "",
    id: "",
    password: "",
    confirmPassword: "",
  });
  const [agreeToTerms, setAgreeToTerms] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  // Track validation status
  const [validations, setValidations] = useState({
    nicknameChecked: false,
    idChecked: false,
  });

  const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });

    // Reset validation when user changes related fields
    if (name === "nickname") {
      setValidations((prev) => ({ ...prev, nicknameChecked: false }));
    } else if (name === "id") {
      setValidations((prev) => ({ ...prev, idChecked: false }));
    }

    // Clear error when user starts typing
    setErrorMessage("");
  };

  const checkDuplicate = (type: "nickname" | "id") => {
    // Simulate checking for duplicates
    const fieldValue = formData[type];

    if (!fieldValue) {
      setErrorMessage(
        `${type === "nickname" ? "닉네임" : "아이디"}을 먼저 입력해주세요.`
      );
      return;
    }

    // Here you would typically call an API to check for duplicates
    // For demonstration, we'll simulate a successful check
    setTimeout(() => {
      setValidations((prev) => ({
        ...prev,
        [type === "nickname" ? "nicknameChecked" : "idChecked"]: true,
      }));
      setErrorMessage(
        `${type === "nickname" ? "닉네임" : "아이디"} 사용 가능합니다.`
      );
    }, 500);
  };

  const handleSubmit = (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setErrorMessage("");

    // Validate inputs
    if (!formData.nickname || !formData.password || !formData.id) {
      setErrorMessage("모든 필드를 입력해주세요.");
      return;
    }

    if (formData.password !== formData.confirmPassword) {
      setErrorMessage("비밀번호가 일치하지 않습니다.");
      return;
    }

    if (!validations.nicknameChecked) {
      setErrorMessage("닉네임 중복확인이 필요합니다.");
      return;
    }

    if (!validations.idChecked) {
      setErrorMessage("아이디 중복확인이 필요합니다.");
      return;
    }

    if (!agreeToTerms) {
      setErrorMessage("이용약관에 동의해주세요.");
      return;
    }

    console.log("회원가입 요청", formData);
    // API 호출 로직
    setErrorMessage("회원가입 처리 중...");

    // Simulate API call
    setTimeout(() => {
      setErrorMessage("회원가입이 완료되었습니다!");
      // Here you would typically redirect to login page
      // window.location.href = '/login';
    }, 1000);
  };

  // Check if form is valid
  const isFormValid = () => {
    return (
      formData.nickname &&
      formData.id &&
      formData.password &&
      formData.password === formData.confirmPassword &&
      validations.nicknameChecked &&
      validations.idChecked &&
      agreeToTerms
    );
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen py-12 px-4 sm:px-6 lg:px-8 bg-white">
      <div className="w-full max-w-md">
        {/* 로고 */}
        <div className="flex justify-center mb-4">
          <div className="relative w-16 h-16">
            <Image
              src="/logo.png"
              alt="Logo"
              width={64}
              height={64}
              className="w-full h-full"
            />
          </div>
        </div>

        {/* 타이틀 */}
        <h2 className="text-center text-2xl font-bold text-purple-600 mb-8">
          회원가입
        </h2>

        {/* 에러 메시지 */}
        {errorMessage && (
          <div
            className={`mb-4 p-3 rounded-md text-sm ${
              errorMessage.includes("가능") || errorMessage.includes("완료")
                ? "bg-green-50 text-green-700"
                : "bg-red-50 text-red-700"
            }`}
          >
            {errorMessage}
          </div>
        )}

        {/* 폼 */}
        <form onSubmit={handleSubmit} className="space-y-4">
          {/* 닉네임 */}
          <div>
            <div className="flex justify-between items-center mb-1">
              <label
                htmlFor="nickname"
                className="block text-sm font-medium text-gray-700"
              >
                닉네임
              </label>
              <button
                type="button"
                onClick={() => checkDuplicate("nickname")}
                className="px-2 py-1 text-xs bg-gray-100 hover:bg-gray-200 rounded text-gray-700 transition duration-150"
              >
                중복확인
              </button>
            </div>
            <input
              id="nickname"
              name="nickname"
              type="text"
              required
              placeholder="닉네임을 입력하세요"
              value={formData.nickname}
              onChange={handleChange}
              className={`w-full px-3 py-2 border ${
                validations.nicknameChecked
                  ? "border-green-500"
                  : "border-gray-300"
              } rounded-md shadow-sm focus:outline-none focus:ring-purple-500 focus:border-purple-500`}
            />
          </div>

          {/* 휴대폰 번호 */}
          <div>
            <label
              htmlFor="phone"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              휴대폰 번호
            </label>
            <input
              id="phone"
              name="phone"
              type="tel"
              required
              placeholder="휴대폰 번호를 입력하세요"
              value={formData.phone}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-purple-500 focus:border-purple-500"
            />
          </div>

          {/* 아이디 */}
          <div>
            <div className="flex justify-between items-center mb-1">
              <label
                htmlFor="id"
                className="block text-sm font-medium text-gray-700"
              >
                아이디
              </label>
              <button
                type="button"
                onClick={() => checkDuplicate("id")}
                className="px-2 py-1 text-xs bg-gray-100 hover:bg-gray-200 rounded text-gray-700 transition duration-150"
              >
                중복확인
              </button>
            </div>
            <input
              id="id"
              name="id"
              type="text"
              required
              placeholder="아이디를 입력하세요"
              value={formData.id}
              onChange={handleChange}
              className={`w-full px-3 py-2 border ${
                validations.idChecked ? "border-green-500" : "border-gray-300"
              } rounded-md shadow-sm focus:outline-none focus:ring-purple-500 focus:border-purple-500`}
            />
          </div>

          {/* 비밀번호 */}
          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              비밀번호
            </label>
            <input
              id="password"
              name="password"
              type="password"
              required
              placeholder="비밀번호를 입력하세요"
              value={formData.password}
              onChange={handleChange}
              className="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-purple-500 focus:border-purple-500"
            />
          </div>

          {/* 비밀번호 확인 */}
          <div>
            <label
              htmlFor="confirmPassword"
              className="block text-sm font-medium text-gray-700 mb-1"
            >
              비밀번호 확인
            </label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              required
              placeholder="비밀번호를 다시 입력하세요"
              value={formData.confirmPassword}
              onChange={handleChange}
              className={`w-full px-3 py-2 border ${
                formData.confirmPassword &&
                formData.password === formData.confirmPassword
                  ? "border-green-500"
                  : "border-gray-300"
              } rounded-md shadow-sm focus:outline-none focus:ring-purple-500 focus:border-purple-500`}
            />
          </div>

          {/* 이용약관 */}
          <div className="flex items-center mt-4">
            <input
              id="agreeToTerms"
              name="agreeToTerms"
              type="checkbox"
              checked={agreeToTerms}
              onChange={(e) => setAgreeToTerms(e.target.checked)}
              className="h-4 w-4 text-purple-600 focus:ring-purple-500 border-gray-300 rounded"
            />
            <label
              htmlFor="agreeToTerms"
              className="ml-2 block text-sm text-gray-600"
            >
              이용약관 및 개인정보처리방침에 동의합니다
            </label>
          </div>

          {/* 회원가입 버튼 */}
          <div className="pt-4">
            <button
              type="submit"
              className={`w-full flex justify-center py-3 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white ${
                isFormValid()
                  ? "bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500"
                  : "bg-purple-400 cursor-default"
              }`}
            >
              회원가입
            </button>
          </div>

          {/* 간편 회원가입 */}
          <div className="pt-2">
            <div className="text-center text-sm text-gray-500 my-3">
              간편 회원가입
            </div>
            <button
              type="button"
              className="w-full flex justify-center items-center py-3 px-4 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-yellow-300 hover:bg-yellow-400 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500"
              onClick={() => {
                console.log("카카오톡 로그인 시도");
                // 카카오 로그인 API 호출
              }}
            >
              <span className="mr-2">💬</span>
              카카오톡으로 회원가입
            </button>
          </div>

          {/* 로그인 링크 */}
          <div className="text-center mt-4">
            <span className="text-sm text-gray-500">
              이미 계정이 있으신가요?
            </span>{" "}
            <Link
              href="/login"
              className="text-sm font-medium text-gray-900 hover:text-purple-600"
            >
              로그인
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
