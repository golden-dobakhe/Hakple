'use client';

import Link from 'next/link';
import { useState } from 'react';

export default function Home() {
  console.log(process.env.NEXT_PUBLIC_API_BASE_URL);
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <>
      <header className="bg-[#f2edf4] py-3 sticky top-0 z-10 shadow-sm">
        <div className="max-w-screen-lg mx-auto px-4">
          <div className="flex items-center justify-between">
            {/* 왼쪽: 로고와 네비게이션 */}
            <div className="flex items-center space-x-4 md:space-x-8">
              {/* 모바일 메뉴 버튼 - 왼쪽으로 이동 */}
              <button
                className="md:hidden p-2 text-gray-500 rounded-md hover:bg-gray-100 focus:outline-none"
                onClick={() => setIsMenuOpen(!isMenuOpen)}
              >
                <svg
                  className="w-6 h-6"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  {isMenuOpen ? (
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M6 18L18 6M6 6l12 12"
                    />
                  ) : (
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth="2"
                      d="M4 6h16M4 12h16M4 18h16"
                    />
                  )}
                </svg>
              </button>

              <Link href="/" className="flex items-center flex-shrink-0">
                <img
                  src="logo.png"
                  alt="HAKPLE"
                  width={55}
                  height={55}
                  className="logo"
                />
              </Link>

              {/* 데스크탑 메뉴 */}
              <nav className="hidden md:flex space-x-5 lg:space-x-8">
                <Link
                  href="/home"
                  className="font-medium text-lg text-gray-700 hover:text-gray-900 whitespace-nowrap hover:font-semibold transition-all"
                >
                  홈
                </Link>
                <Link
                  href="/academy"
                  className="font-medium text-lg text-gray-700 hover:text-gray-900 whitespace-nowrap hover:font-semibold transition-all"
                >
                  게시판
                </Link>
                <Link
                  href="/boad"
                  className="font-medium text-lg text-gray-700 hover:text-gray-900 whitespace-nowrap hover:font-semibold transition-all"
                >
                  인기글
                </Link>
                <Link
                  href="/community"
                  className="font-medium text-lg text-gray-700 hover:text-gray-900 whitespace-nowrap hover:font-semibold transition-all"
                >
                  캘린더
                </Link>
              </nav>
            </div>

            {/* 오른쪽: 검색과 로그인 */}
            <div className="flex items-center space-x-2 md:space-x-3">
              <div className="relative w-full max-w-[180px] md:max-w-[220px]">
                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                  <svg
                    className="w-4 h-4 md:w-5 md:h-5 text-gray-400"
                    fill="none"
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth="2"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"></path>
                  </svg>
                </div>
                <input
                  type="search"
                  className="block w-full pl-8 md:pl-10 pr-3 py-2 border border-gray-300 rounded-md leading-5 bg-white focus:outline-none focus:ring-purple-500 focus:border-purple-500 text-sm"
                  placeholder="검색어를 입력하세요"
                />
              </div>
              <button className="bg-purple-500 hover:bg-purple-600 text-white font-medium py-2 px-4 md:px-6 rounded-md text-sm whitespace-nowrap h-[38px]">
                로그인
              </button>
            </div>
          </div>

          {/* 모바일 메뉴 */}
          {isMenuOpen && (
            <div className="mt-3 md:hidden">
              <nav className="flex flex-col space-y-2 py-2">
                <Link
                  href="/home"
                  className="font-medium text-base text-gray-700 hover:text-gray-900 px-2 py-2 rounded-md hover:bg-gray-100"
                >
                  홈
                </Link>
                <Link
                  href="/academy"
                  className="font-medium text-base text-gray-700 hover:text-gray-900 px-2 py-2 rounded-md hover:bg-gray-100"
                >
                  게시판
                </Link>
                <Link
                  href="/boad"
                  className="font-medium text-base text-gray-700 hover:text-gray-900 px-2 py-2 rounded-md hover:bg-gray-100"
                >
                  인기글
                </Link>
                <Link
                  href="/community"
                  className="font-medium text-base text-gray-700 hover:text-gray-900 px-2 py-2 rounded-md hover:bg-gray-100"
                >
                  캘린더
                </Link>
              </nav>
            </div>
          )}
        </div>
      </header>

      <main className="flex flex-col items-center justify-center py-12 px-4 max-w-screen-lg mx-auto">
        <section className="w-full text-center mb-15">
          <h1 className="text-3xl font-bold mb-4">
            학원 생활의 시작,
            <br />
            지금 학플과 함께하세요!
          </h1>
          <p className="text-gray-600 mb-9">
            3분 만에 가입하고
            <br />내 학원 정보와 커뮤니티를 무료로 이용해보세요
          </p>
          <button className="bg-purple-500 hover:bg-purple-600 text-white font-medium py-3 px-8 rounded-md">
            3분 만에 회원가입 하기
          </button>
        </section>

        <section className="w-full mb-10 py-5">
          <h2 className="text-2xl font-bold text-center mb-12">
            회원가입 시 누릴 수 있는 혜택
          </h2>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="flex flex-col items-center text-center">
              <div className="p-7 mb-7 bg-purple-100 rounded-full">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-14 w-14 text-purple-500"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
                  />
                </svg>
              </div>
              <h3 className="text-xl font-bold mb-4">무료 커뮤니티 이용</h3>
              <p className="text-gray-600">
                학원생들과 자유롭게 소통하며 유용한 정보를 교환해보세요.
              </p>
            </div>

            <div className="flex flex-col items-center text-center">
              <div className="p-7 mb-7 bg-purple-100 rounded-full">
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  className="h-14 w-14 text-purple-500"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M11.049 2.927c.3-.921 1.603-.921 1.902 0l1.519 4.674a1 1 0 00.95.69h4.915c.969 0 1.371 1.24.588 1.81l-3.976 2.888a1 1 0 00-.363 1.118l1.518 4.674c.3.922-.755 1.688-1.538 1.118l-3.976-2.888a1 1 0 00-1.176 0l-3.976 2.888c-.783.57-1.838-.197-1.538-1.118l1.518-4.674a1 1 0 00-.363-1.118l-3.976-2.888c-.784-.57-.38-1.81.588-1.81h4.914a1 1 0 00.951-.69l1.519-4.674z"
                  />
                </svg>
              </div>
              <h3 className="text-xl font-bold mb-4">실시간 리뷰</h3>
              <p className="text-gray-600">
                실제 학생들의 생생한 학원 리뷰를 확인해보세요.
              </p>
            </div>
          </div>
        </section>

        <section className="w-full text-center mb-10">
          <div className="mb-3">
            <div className="w-40 h-40 mx-auto flex items-center justify-center">
              <img
                src="logo.png"
                alt="지금 바로 시작하세요"
                width={200}
                height={200}
                className="mx-auto"
              />
            </div>
          </div>
          <h2 className="text-2xl font-bold mb-4">지금 바로 시작하세요</h2>
          <p className="text-gray-600 mb-6">
            더 이상 고민하지 마세요.
            <br />
            학플에서 여러분이 원하는 학원생활을 만들어보세요.
          </p>
        </section>
      </main>
    </>
  );
}
