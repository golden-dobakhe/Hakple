"use client";

import Link from "next/link";

export default function SignupSuccess() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-white">
      <div className="w-full max-w-md text-center px-6">
        {/* 타이틀 */}
        <h2 className="text-xl font-medium text-purple-500 mb-5">
          회원가입 완료
        </h2>

        {/* 체크 아이콘 */}
        <div className="flex justify-center mb-5">
          <div className="bg-gray-700 rounded-full p-3 w-14 h-14 flex items-center justify-center">
            <svg
              className="w-7 h-7 text-white"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2.5}
                d="M5 13l4 4L19 7"
              />
            </svg>
          </div>
        </div>

        {/* 완료 메시지 */}
        <p className="text-lg text-gray-800 mb-2">회원가입이 완료되었습니다!</p>

        {/* 서브 메시지 */}
        <p className="text-sm text-gray-500 mb-6">
          이제 학플의 모든 서비스를 이용하실 수 있습니다.
        </p>

        {/* 로그인 버튼 */}
        <Link
          href="/login"
          className="block w-full py-3 rounded-md text-center text-sm font-medium text-white bg-purple-500 hover:bg-purple-600 focus:outline-none mb-3"
        >
          로그인
        </Link>

        {/* 홈으로 가기 링크 */}
        <div>
          <Link href="/" className="text-sm text-gray-500 hover:text-gray-700">
            홈으로 돌아가기
          </Link>
        </div>
      </div>
    </div>
  );
}
