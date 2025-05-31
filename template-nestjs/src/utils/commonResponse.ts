enum ResponseCode {
    SUCCESS = 200,
    BAD_REQUEST = 400,
    UNAUTHORIZED = 401,
    FORBIDDEN = 403,
    NOT_FOUND = 404,
    INTERNAL_SERVER_ERROR = 500,
}

export class CommonResponse<T> {
    public code: ResponseCode;
    public message: string;
    public data: T | null;
    public timestamp: number;

    constructor(code: ResponseCode, message: string, data: T | null = null) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.timestamp = Date.now();
    }

    // 成功响应（不带数据）
    public static success(): CommonResponse<null> {
        return new CommonResponse(ResponseCode.SUCCESS, "Operation succeeded");
    }

    // 成功响应（带数据）
    public static successWithData<T>(data: T): CommonResponse<T> {
        return new CommonResponse<T>(ResponseCode.SUCCESS, "Operation succeeded", data);
    }

    // 失败响应（使用预定义错误码）
    public static error(code: ResponseCode, message?: string): CommonResponse<null> {
        const errorMessage = message || this.getDefaultMessage(code);
        return new CommonResponse<null>(code, errorMessage);
    }

    // 获取默认错误消息
    private static getDefaultMessage(code: ResponseCode): string {
        switch (code) {
            case ResponseCode.BAD_REQUEST:
                return "Bad request";
            case ResponseCode.UNAUTHORIZED:
                return "Unauthorized";
            case ResponseCode.FORBIDDEN:
                return "Forbidden";
            case ResponseCode.NOT_FOUND:
                return "Resource not found";
            case ResponseCode.INTERNAL_SERVER_ERROR:
                return "Internal server error";
            default:
                return "Unknown error";
        }
    }

    // 判断是否成功
    public isSuccess(): boolean {
        return this.code === ResponseCode.SUCCESS;
    }
}

