'use client'

import React, { useState, useEffect } from 'react'
import dynamic from 'next/dynamic'
import { useRouter, useParams } from 'next/navigation'

// TiptapEditor를 동적으로 불러오기 (SSR 비활성화)
const TiptapEditor = dynamic(() => import('@/components/editor/TiptapEditor'), { ssr: false })

// --- 태그 입력 컴포넌트 ---
interface TagInputProps {
    tags: string[]
    onTagsChange: (tags: string[]) => void
}

/**
 * IME(Input Method Editor)란?
 * - 한글, 중국어, 일본어 등과 같은 조합형 문자를 입력할 때 사용되는 입력 방식
 * - 예: 한글 '가'를 입력할 때 'ㄱ'+'ㅏ'를 조합하는 과정이 있음
 * - IME 입력 중에는 composition 이벤트가 발생하며, 조합이 끝나기 전에 Enter나 다른 키 이벤트가
 *   중간에 처리되면 원치 않는 동작이 발생할 수 있음 (마지막 글자만 별도 태그로 추가되는 문제)
 */

// 문자열을 태그로 변환하는 헬퍼 함수 - 절대 분리하지 않음
const sanitizeTag = (input: string): string => {
    // 1. 입력값 유효성 검사
    // 인풋이 없거나 공백인 경우 빈 문자열 반환
    if (!input || typeof input !== 'string') return ''

    // 2. 앞뒤 공백 제거
    const trimmed = input.trim()
    if (!trimmed) return ''

    // 3. #으로 시작하면 # 제거
    return trimmed.startsWith('#') ? trimmed.substring(1).trim() : trimmed
}

const TagInput: React.FC<TagInputProps> = ({ tags, onTagsChange }) => {
    // 상태 관리
    const [inputValue, setInputValue] = useState('') // 입력 필드 값
    const [isComposing, setIsComposing] = useState(false) // IME 입력 상태 (조합 중인지 여부)

    /**
     * 입력 필드 변경 처리 함수
     * 순서:
     * 1. 입력값 가져오기
     * 2. 쉼표가 포함된 경우 특별 처리
     * 3. 일반 입력의 경우 상태 업데이트
     */
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value

        // 쉼표가 포함된 경우 특별 처리 (태그 추가)
        if (value.includes(',')) {
            // 쉼표를 기준으로 분리
            const parts = value.split(',')
            if (parts.length > 1) {
                // 마지막 부분을 제외한 모든 부분은 태그로 추가
                for (let i = 0; i < parts.length - 1; i++) {
                    const part = parts[i].trim()
                    if (part) {
                        addTag(part) // 태그 추가
                    }
                }
                // 마지막 부분은 새 입력값으로 설정
                setInputValue(parts[parts.length - 1])
                return
            }
        }

        // 일반적인 경우 입력값 업데이트
        setInputValue(value)
    }

    /**
     * 태그 추가 핵심 함수
     * 순서:
     * 1. 입력값 검증
     * 2. 태그 생성 (sanitizeTag 함수 사용)
     * 3. 중복 검사
     * 4. 태그 추가 및 결과 반환
     */
    const addTag = (rawInput: string) => {
        // 1. 입력값 검증
        if (!rawInput || !rawInput.trim()) return false

        // 2. 태그 생성
        const newTag = sanitizeTag(rawInput)
        if (!newTag) return false

        // 3. 중복 검사
        if (tags.includes(newTag)) return false

        // 4. 태그 추가
        onTagsChange([...tags, newTag])
        return true
    }

    /**
     * 현재 입력값으로 태그 추가
     * - addTag 함수를 호출하여 현재 입력값을 태그로 추가
     * - 성공하면 입력 필드 초기화
     */
    const addCurrentTag = () => {
        if (addTag(inputValue)) {
            setInputValue('') // 성공적으로 추가된 경우만 입력값 초기화
        }
    }

    /**
     * 키 입력 처리 함수
     * 순서:
     * 1. IME 입력 중인지 확인
     * 2. 엔터키 처리
     * 3. 쉼표 처리
     */
    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        // IME 입력 중이면 처리하지 않음 - 이것이 매우 중요!
        // 한글 등의 조합 문자가 완성되기 전에 이벤트가 발생하면 잘못된 입력이 될 수 있음
        if (isComposing) return

        if (e.key === 'Enter') {
            e.preventDefault()
            addCurrentTag()
        } else if (e.key === ',') {
            e.preventDefault()
            // 쉼표 입력은 handleInputChange에서 처리되므로 여기서는 아무것도 하지 않음
        }
    }

    /**
     * IME 입력 시작 이벤트 처리
     * - 조합형 문자 입력이 시작될 때 호출됨
     * - 한글/중국어/일본어 등 입력 시 활성화
     */
    const handleCompositionStart = () => {
        setIsComposing(true)
    }

    /**
     * IME 입력 종료 이벤트 처리
     * - 조합형 문자 입력이 완료될 때 호출됨
     * - 입력이 완료된 후 상태 업데이트
     */
    const handleCompositionEnd = () => {
        setIsComposing(false)
    }

    /**
     * 포커스가 벗어날 때 태그 추가
     * - 입력 필드에서 포커스가 빠져나갈 때 호출됨
     * - IME 입력 중이 아닌 경우에만 태그 추가
     */
    const handleBlur = () => {
        if (inputValue.trim() && !isComposing) {
            addCurrentTag()
        }
    }

    /**
     * 태그 삭제 함수
     * - 특정 태그를 배열에서 제거
     */
    const removeTag = (tagToRemove: string) => {
        const updatedTags = tags.filter((tag) => tag !== tagToRemove)
        onTagsChange(updatedTags)
    }

    return (
        <div>
            <div className="flex flex-wrap items-center gap-[5px] w-full border-none py-[8px] px-3 bg-[#ffffff]">
                {/* 태그 렌더링 */}
                {tags.map((tag, index) => (
                    <div
                        key={`tag-${index}-${tag}`}
                        className="flex items-center bg-[#980ffa] text-[#ffffff] rounded-full p-[5px] cursor-pointer hover:bg-[#8a40c0] transition-all"
                        onClick={() => removeTag(tag)}
                        title="클릭하여 제거"
                    >
                        <span className="text-sm font-medium">{tag}</span>
                    </div>
                ))}

                {/* 입력 필드 - 이벤트 핸들러 개선 */}
                <input
                    type="text"
                    value={inputValue}
                    onChange={handleInputChange}
                    onKeyDown={handleKeyDown}
                    onBlur={handleBlur}
                    onCompositionStart={handleCompositionStart}
                    onCompositionEnd={handleCompositionEnd}
                    placeholder="    태그 입력 (쉼표 또는 엔터키로 등록)"
                    className="flex-grow min-w-[180px] py-[6px] px-[15px] text-sm focus:outline-none border-none bg-transparent"
                />
            </div>
        </div>
    )
}

// --- 게시글 수정 페이지 컴포넌트 ---
const EditPostPage = () => {
    const router = useRouter()
    const params = useParams()
    const postId = params.id as string

    const [title, setTitle] = useState('')
    const [content, setContent] = useState('')
    const [tags, setTags] = useState<string[]>([])
    const [isSubmitting, setIsSubmitting] = useState(false)
    const [isLoading, setIsLoading] = useState(true)
    const [error, setError] = useState('')

    // 게시글 데이터 불러오기
    useEffect(() => {
        const fetchPostData = async () => {
            try {
                setIsLoading(true)
                const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/posts/${postId}`)

                if (!response.ok) {
                    throw new Error('게시글을 불러오는데 실패했습니다.')
                }

                const postData = await response.json()

                // 데이터 설정
                setTitle(postData.title || '')
                setContent(postData.content || '')
                setTags(postData.tags || [])
            } catch (err) {
                console.error('게시글 데이터 로딩 중 오류:', err)
                setError(err instanceof Error ? err.message : '게시글을 불러오는데 실패했습니다.')
            } finally {
                setIsLoading(false)
            }
        }

        if (postId) {
            fetchPostData()
        }
    }, [postId])

    // 태그 변경 핸들러 - 서버로 전송하기 전에 중복 확인 추가
    const handleTagsChange = (newTags: string[]) => {
        // 중복 태그를 제거하여 설정 (서버에서 분리된 태그가 있을 경우 대비)
        const uniqueTags = [...new Set(newTags)]
        setTags(uniqueTags)
    }

    const handleSubmit = async () => {
        if (!title.trim()) {
            setError('제목을 입력해주세요.')
            return
        }

        if (!content.trim()) {
            setError('내용을 입력해주세요.')
            return
        }

        try {
            setIsSubmitting(true)
            setError('')

            // 빈 태그만 필터링하고, 태그 형식을 그대로 유지
            const finalTags = tags.filter((tag) => tag.trim() !== '')

            // API 호출을 위한 데이터 구성
            const postData = {
                title,
                content,
                tags: finalTags, // 태그 배열을 그대로 전송
            }

            const response = await fetch(`${process.env.NEXT_PUBLIC_API_BASE_URL}/api/v1/posts/${postId}`, {
                method: 'PUT', // 수정이므로 PUT 메서드 사용
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(postData),
            })

            if (!response.ok) {
                let errorMsg = '게시글 수정에 실패했습니다.'
                try {
                    const contentType = response.headers.get('content-type')
                    if (contentType && contentType.indexOf('application/json') !== -1) {
                        const errorData = await response.json()
                        errorMsg = errorData.message || errorMsg
                    } else {
                        errorMsg = `서버 오류: ${response.status}`
                    }
                } catch (e) {
                    errorMsg = `서버 오류: ${response.status}`
                }
                throw new Error(errorMsg)
            }

            // 성공 시 게시글 상세 페이지로 이동
            router.push(`/post/${postId}`)
        } catch (err) {
            console.error('게시글 수정 중 오류:', err)
            setError(err instanceof Error ? err.message : '게시글 수정에 실패했습니다.')
        } finally {
            setIsSubmitting(false)
        }
    }

    return (
        <>
            <main className="container mx-auto p-[15px] sm:p-[20px] md:p-[30px] max-w-[1200px]">
                <h1 className="text-xl sm:text-2xl font-semibold mb-4 sm:mb-6">게시글 수정</h1>
                {isLoading ? (
                    <div className="flex justify-center items-center h-[300px]">
                        <p>게시글 불러오는 중...</p>
                    </div>
                ) : (
                    <div className="bg-white p-4 sm:p-6 rounded-lg shadow-md w-full border border-[#F9FAFB]">
                        {/* 제목 입력 */}
                        <div className="w-full mb-3 sm:mb-4 border border-[#F9FAFB] rounded-md overflow-hidden pb-[10px]">
                            <div className="p-2 sm:p-3">
                                <input
                                    type="text"
                                    value={title}
                                    onChange={(e) => setTitle(e.target.value)}
                                    placeholder="  제목을 입력해주세요"
                                    className="w-full border border-[#F9FAFB] rounded-md py-[14px] px-[15px] px-3 text-sm focus:outline-none"
                                />
                            </div>
                        </div>

                        {/* Tiptap 에디터 적용 */}
                        <div className="w-full mb-3 sm:mb-4 border border-[#F9FAFB] rounded-md overflow-hidden">
                            <div className="p-2 sm:p-3 min-h-[400px] sm:min-h-[500px] md:min-h-[600px] lg:min-h-[700px]">
                                <TiptapEditor content={content} onChange={setContent} />
                            </div>
                        </div>

                        {/* 태그 입력 */}
                        <div className="w-full mb-4 sm:mb-6 border border-[#F9FAFB] rounded-md overflow-hidden pb-[10px]">
                            <div className="p-2 sm:p-3">
                                <TagInput tags={tags} onTagsChange={handleTagsChange} />
                            </div>
                        </div>

                        {/* 에러 메시지 */}
                        {error && <div className="w-full mb-4 text-red-500 text-sm">{error}</div>}

                        {/* 수정 버튼 */}
                        <div className="w-full border border-[#F9FAFB] rounded-[10px] overflow-hidden">
                            <div className="p-2 sm:p-3">
                                <div className="flex justify-between">
                                    <button
                                        onClick={() => router.push('/post')}
                                        className="bg-[#980ffa] text-[#ffffff] py-[10px] px-[20px] rounded-[10px] border-none text-[12px]"
                                    >
                                        목록
                                    </button>
                                    <button
                                        onClick={handleSubmit}
                                        disabled={isSubmitting}
                                        className="bg-[#980ffa] text-[#ffffff] py-[10px] px-[20px] rounded-[10px] border-none text-[12px]"
                                    >
                                        {isSubmitting ? '수정 중...' : '수정하기'}
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                )}
            </main>
        </>
    )
}

export default EditPostPage