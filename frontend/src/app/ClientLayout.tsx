'use client'

import { useEffect } from 'react'
import { useLoginMember, LoginMemberContext } from '@/stores/auth/loginMember'
import Header from '@/components/Header'
import Footer from '@/components/Footer'

export function ClientLayout({ children }: { children: React.ReactNode }) {
    const { loginMember, setLoginMember, setNoLoginMember, isLoginMemberPending, isLogin, logout, logoutAndHome } =
        useLoginMember()

    //전역 Store등록, context api기술을 썼다고 함
    const loginMemberContextValue = {
        loginMember,
        setLoginMember,
        setNoLoginMember,
        isLoginMemberPending,
        isLogin,
        logout,
        logoutAndHome,
    }

    //[]최초 요청시 api를 보낸다, 요청시에도 저게 돌아간다고 한다
    useEffect(() => {
        console.log('ClientLayout - 로그인 상태 확인 시작')

        fetch('/api/v1/auth/me', {
            //요청마다 헤더에 쿠키가 자동 실행된다고 한다
            credentials: 'include',
        })
            .then((res) => {
                console.log('로그인 상태 응답:', res.status)
                if (!res.ok) {
                    throw new Error('인증 실패')
                }
                return res.json()
            })
            .then((data) => {
                //로그인 성공시
                console.log('로그인 상태 성공 데이터:', data)
                setLoginMember(data)
            })
            .catch((error) => {
                //로그인이 안되있다면
                console.error('로그인 상태 확인 에러:', error)
                setNoLoginMember()
            })
            .finally(() => {
                console.log('로그인 상태 확인 완료, 현재 로그인 상태:', isLogin)
            })
    }, [])

    useEffect(() => {
        console.log('로그인 상태 변경:', isLogin, loginMember)
    }, [isLogin, loginMember])

    if (isLoginMemberPending) {
        return <div className="flex justify-center items-center h-screen">로그인 중...</div>
    }

    return (
        //나중에 내부적으로 접근이 가능하게 된다, 그리고 value를 통하여 전역적으로 접근이 가능하게 된다
        <LoginMemberContext.Provider value={loginMemberContextValue}>
            <div className="flex flex-col min-h-screen">
                <Header />
                <div className="flex-grow">{children}</div>
                <Footer />
            </div>
        </LoginMemberContext.Provider>
    )
}
